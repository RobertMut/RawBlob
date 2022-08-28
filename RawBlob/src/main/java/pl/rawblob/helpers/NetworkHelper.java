package pl.rawblob.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * NetworkHelper class
 */
public class NetworkHelper {

    /**
     * Logger
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(NetworkHelper.class);

    /**
     * Reads incoming input
     * @implSpec Reads data up to 32768 bytes buffer then ends with /r/n characters and trim then before returning
     * @param socket Connected socket
     * @return Incoming string
     */
    public static String read(Socket socket){
        try
        {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            byte[] data;
            String input = "";
            boolean terminated = false;

            while(!terminated){
                data = new byte[32768];

                dataInputStream.read(data, 0, data.length);
                input += new String(data, 0, data.length);

                if(input.contains("\r\n"))
                {
                    terminated = true;
                    input = input.replace("\r\n", "");
                }
            }

            return input.trim();
        }
        catch (IOException ioException)
        {
            LOGGER.error(ioException.getMessage());
            return null;
        }
    }

    /**
     * Writes data to connected socket
     * @implSpec Writes utf to connected socket with given message
     * @param socket socket
     * @param message message to send
     */
    public static void write(Socket socket, String message){
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException ioException){
            LOGGER.error(ioException.getMessage());
        }
    }
}
