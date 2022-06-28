package pl.rawblobclient.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawblobclient.dtos.BlobDto;
import pl.rawblobclient.dtos.BlobsListDto;
import pl.rawblobclient.helpers.JsonHelper;
import pl.rawblobclient.helpers.NetworkHelper;
import pl.rawblobclient.interfaces.services.ICommandService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Base64;

@Component
public class CommandService implements ICommandService {

    private Socket socket;
    private String address;
    private String port;

    @Autowired
    public CommandService(String address, String port) throws IOException {
        this.address = address;
        this.port = port;
        //socket.bind(new InetSocketAddress(address, Integer.parseInt(port)));
    }

    @Override
    public BlobsListDto ListBlobs(){
        try {
            socket = new Socket(address, Integer.parseInt(port));

            String response = NetworkHelper.send(socket, "List\r\n");
            var serializedResponse = JsonHelper.deserialize(response, new BlobsListDto(null).getClass());

            return serializedResponse;
        } catch (IOException exception){
            return null;
        }
    }

    @Override
    public void DownloadBlob(String blobName){
        try {
            socket = new Socket(address, Integer.parseInt(port));

            String response = NetworkHelper.send(socket, "Download "+blobName+"\r\n");
            var serializedResponse = JsonHelper.deserialize(response, new BlobDto(null).getClass());

            byte[] dataBytes = Base64.getDecoder().decode(serializedResponse.Blob.Data);

            FileOutputStream fileOutputStream = new FileOutputStream(serializedResponse.Blob.BlobName);
            fileOutputStream.write(dataBytes);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void Delete(String blobName){
        NetworkHelper.send(socket, "Delete "+blobName+"\r\n");
    }

    @Override
    public void Upload(String filename, InputStream stream){
        StringBuilder sb = new StringBuilder();

        try {
            byte[] data = stream.readAllBytes();

            sb.append("Upload ");
            sb.append(filename);
            sb.append(Base64.getEncoder().encodeToString(data)).append("\r\n");

            NetworkHelper.send(socket, sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
