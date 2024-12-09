package andrewjf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Creates a new socket connection to the server
     * @param ip
     * @param port
     * @throws IOException
     */
    public void start(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));
    }

    /**
     * Sends a message to the server and returns the response
     * @param message
     * @return
     * @throws IOException
     */
    public String send(String message) throws IOException {
        out.println(message);
        return in.readLine();
    }

    /**
     * Closes the socket connection
     * @throws IOException
     */
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
