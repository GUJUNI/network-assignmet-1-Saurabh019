//multithreaded tcp client and server program to prove faraday's law in java

import java.io.*;
import java.net.*;

public class FaradaysLawServer {
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
            if (request.equals("EMF")) {
                // Simulate changing magnetic field
                double magneticFieldStrength = 0.5; // Magnetic field strength (in tesla)
                double timePeriod = 2.0; // Time period (in seconds)

                // Calculate induced electromotive force (EMF) using Faraday's law
                double emf = -magneticFieldStrength * timePeriod;

                // Send the induced EMF to the client
                out.println(emf);
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
