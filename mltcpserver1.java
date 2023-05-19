//multithreaded TCP client and server program to calculate the distance between milky way galaxy and andromeda galaxy

import java.io.*;
import java.net.*;

public class DistanceServer {
    public static void main(String[] args) {
        try {
            // Create server socket
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server started. Waiting for client connections...");

            while (true) {
                // Accept client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");

                // Create a new thread for the client request
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // Get input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Read client request
            String request = in.readLine();

            // Process the request
            if (request.equals("DISTANCE")) {
                // Calculate and send the distance to the client
                double distance = 2.537e+19; // Example distance (in kilometers)
                out.println(distance);
            } else {
                // Invalid request
                out.println("Invalid request");
            }

            // Close connections
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
