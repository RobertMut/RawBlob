package pl.rawblobclient.services;

import pl.rawblobclient.helpers.JsonHelper;
import pl.rawblobclient.helpers.NetworkHelper;
import pl.rawblobclient.interfaces.services.ICommandService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.*;

/**
 * CommandService class
 */
public class CommandService implements ICommandService {
    private Socket socket;
    private String address;
    private String port;

    /**
     * Constructs CommandService
     * @param address server address
     * @param port server port
     */
    public CommandService(String address, String port) {
        this.address = address;
        this.port = port;
    }

    /**
     * List all blobs
     * @return Blob list in LinkedHashMap
     */
    @Override
    public List<LinkedHashMap<String, String>> listBlobs(){
        try {
            socket = new Socket(address, Integer.parseInt(port));

            NetworkHelper.write(socket, "List\r\n");
            String response = NetworkHelper.receive(socket);
            NetworkHelper.write(socket, "Disconnect\r\n");

            List<LinkedHashMap<String, String>> serializedResponse = JsonHelper.deserialize(response);

            return serializedResponse;
        } catch (IOException exception){
            return null;
        }
    }

    /**
     * Downloads blob
     * @implSpec Download blob in parts of 36768 chunks until message with '<EOF>' is received then writes file to current dir
     * @param blobName blob to be downloaded
     */
    @Override
    public void downloadBlob(String blobName){
        try {
            socket = new Socket(address, Integer.parseInt(port));
            boolean downloadEnded = false;
            String filename = null;
            StringBuilder data = new StringBuilder();

            NetworkHelper.write(socket, "Download "+blobName+"\r\n");

            while(!downloadEnded){
                String response = NetworkHelper.receive(socket);
                LinkedHashMap<String, String> serializedResponse = JsonHelper.deserialize(response);

                if(response.startsWith("{\"Message") && serializedResponse.get("Message").contains("<EOF>")) {
                    downloadEnded = true;
                } else {
                    data.append(serializedResponse.get("data"));
                    filename = serializedResponse.get("blobName");
                }
            }

            NetworkHelper.write(socket, "Disconnect\r\n");

            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            fileOutputStream.write(Base64.getDecoder().decode(data.toString()));
            fileOutputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes blob from storage
     * @param blobName blob name to be deleted
     * @throws IOException when something went wrong
     */
    @Override
    public void delete(String blobName) throws IOException {
        socket = new Socket(address, Integer.parseInt(port));
        NetworkHelper.write(socket, "Delete "+blobName+"\r\n");
        NetworkHelper.receive(socket);
        NetworkHelper.write(socket, "Disconnect\r\n");
    }

    /**
     * Uploads blob to storage
     * @implSpec Uploads file in chunks (32766 bytes per chunk + 2 \r\n characters)
     * @param filename blob name to be uploaded
     * @param stream file stream to be uploaded
     */
    @Override
    public void upload(String filename, InputStream stream){
        try {
            socket = new Socket(address, Integer.parseInt(port));
            byte[] data = stream.readAllBytes();

            NetworkHelper.write(socket, "Upload "+filename+"\r\n");
            NetworkHelper.receive(socket);

            for (int i = 0; i < data.length;){
                byte[] chunk = new byte[Math.min(32766, data.length - i)];

                for(int j = 0; j < chunk.length; j++, i++){
                    chunk[j] = data[i];
                }

                NetworkHelper.write(socket, Base64.getEncoder().encodeToString(chunk)+"\r\n");
            }

            NetworkHelper.write(socket, "<EOF>\r\n");
            NetworkHelper.receive(socket);
            NetworkHelper.write(socket, "Disconnect\r\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
