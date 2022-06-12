package pl.rawblob.helpers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkHelper {

    public static String read(Socket socket){
        try
        {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            byte[] data;
            String input = "";
            boolean terminated = false;
            while(!terminated){
                data = new byte[8192];

                dataInputStream.read(data, 0, data.length);
                input += new String(data, 0, data.length);

                if(input.contains("\r\n"))
                {
                    terminated = true;
                }
            }

            return input.trim();
        }
        catch (IOException ioException)
        {
            return null;
        }
    }

    public static void write(Socket socket, String message){
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException ioException){

        }

    }
}
