package andrewjf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));
    }

    public String send(String message) throws IOException {
        out.println(message);
        return in.readLine();
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
