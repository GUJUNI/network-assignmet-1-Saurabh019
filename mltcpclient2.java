import java.io.*;
import java.net.*;

public class FaradaysLawClient {
    public static void main(String[] args) {
        try {
            // Create socket and connect to the server
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected to server.");

            // Get input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Send request to the server
            out.println("EMF");

            // Receive the induced EMF from the server
            double emf = Double.parseDouble(in.readLine());

            // Display the result
            System.out.println("Induced EMF: " + emf + " volts");

            // Close connections
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
