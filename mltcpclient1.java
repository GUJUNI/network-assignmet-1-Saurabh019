import java.io.*;
import java.net.*;

public class DistanceClient {
    public static void main(String[] args) {
        try {
            // Create socket and connect to the server
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected to server.");

            // Get input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Send request to the server
            out.println("DISTANCE");

            // Receive the distance from the server
            double distance = Double.parseDouble(in.readLine());

            // Display the result
            System.out.println("Distance between Milky Way and Andromeda galaxies: " + distance + " kilometers");

            // Close connections
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
