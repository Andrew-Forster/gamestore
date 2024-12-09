package andrewjf.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Handles communication with a single client.
 * Reads messages from the client, processes them, and sends JSON responses.
 */
public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final ParserService parserService;
    private final String clientId;

    /**
     * Constructor that initializes the client handler with a socket and shared DataService.
     * @param clientSocket - the socket for client communication
     * @param dataService - the shared data service instance
     */
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.clientId = clientSocket.getInetAddress().getHostAddress() + "-" + clientSocket.getPort();
        this.parserService = new ParserService();
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("New client connected: " + clientId);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received from " + clientId + ": " + message);

                if (message.equals(".")) {
                    out.println("QUIT"); // Quit message for client
                    break;
                } else {
                    String jsonResponse = parserService.processMessage(message);
                    out.println(jsonResponse); // Send JSON response
                }
            }
        } catch (IOException e) {
            // e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    /**
     * Cleans up client resources by closing input, output streams and the client socket.
     */
    private void cleanup() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            System.out.println("Client disconnected: " + clientId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

