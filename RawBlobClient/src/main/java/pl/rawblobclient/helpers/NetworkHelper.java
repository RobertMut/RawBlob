package pl.rawblobclient.helpers;

import java.io.*;
import java.net.Socket;

/**
 * NetworkHelper class
 */
public class NetworkHelper {

    /**
     * Receives incoming string
     * @param socket connected socket
     * @return string from server
     */
    public static String receive(Socket socket){
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            String line = dataInputStream.readUTF();

            return line;

        } catch (IOException ioException){
            return null;
        }
    }

    /**
     * Writes message to server
     * @param socket connected socket
     * @param message message to be sent
     * @throws IOException If something with sending went wrong
     */
    public static void write(Socket socket, String message) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        dataOutputStream.write(message.getBytes());
        dataOutputStream.flush();
    }
}
