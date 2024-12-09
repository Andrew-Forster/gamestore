package andrewjf.Controllers;

import java.net.ServerSocket;
import java.net.Socket;

import andrewjf.Models.Store;

public class Server {
    private ServerSocket serverSocket;
    protected static Store store = Store.getInstance();

    /**
     * Starts the server on the specified port.
     * @param port
     */
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket.close();
                    System.out.println("Server stopped");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            } catch (Exception e) {
                if (serverSocket.isClosed()) {
                    System.out.println("Server socket closed, stopping server.");
                    break;
                }
                e.printStackTrace();
            }
        }
    }

    /**
     * Stops the server.
     */
    public void stop() {
        try {
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
