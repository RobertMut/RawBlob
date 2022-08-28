package pl.rawblob.model;

import java.net.Socket;

/**
 * Client model
 */
public class Client {
    private boolean terminated;
    private Socket socket;

    //Getters and setters
    public boolean isTerminated() { return terminated; }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
