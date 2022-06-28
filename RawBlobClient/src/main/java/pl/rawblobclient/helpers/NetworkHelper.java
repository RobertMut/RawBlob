package pl.rawblobclient.helpers;

import java.io.*;
import java.net.Socket;

public class NetworkHelper {

    public static String send(Socket socket, String message){
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            var bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder sb = new StringBuilder();

            dataOutputStream.writeUTF(message);
            String line = bufferedReader.readLine();

            while(line != null){
                sb.append(line);
                line = bufferedReader.readLine();
            }

            socket.close();
            return sb.toString();

        } catch (IOException ioException){
            return null;
        }
    }
}
