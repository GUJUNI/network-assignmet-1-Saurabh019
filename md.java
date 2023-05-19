//java server and client program to compute a message digest for a file of any size and any type
//Server code:
import java.io.*;
import java.net.*;
import java.security.*;

public class Server {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Server started. Listening on port 8000...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

            // Create a MessageDigest object to compute the message digest
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Read the file from the client and compute the message digest
            InputStream is = clientSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
            byte[] digest = md.digest();

            // Send the message digest back to the client
            OutputStream os = clientSocket.getOutputStream();
            os.write(digest);

            clientSocket.close();
            System.out.println("Client disconnected.");
        }
    }
}

//client code:

import java.io.*;
import java.net.*;
import java.security.*;

public class Client {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        if (args.length != 2) {
            System.out.println("Usage: java Client <server> <file>");
            return;
        }

        String serverName = args[0];
        String fileName = args[1];

        // Create a MessageDigest object to compute the message digest
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // Read the file and compute the message digest
        FileInputStream fis = new FileInputStream(fileName);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            md.update(buffer, 0, bytesRead);
        }
        byte[] digest = md.digest();

        // Connect to the server and send the file and the message digest
        Socket socket = new Socket(serverName, 8000);
        OutputStream os = socket.getOutputStream();
        os.write(buffer);

        InputStream is = socket.getInputStream();
        byte[] serverDigest = new byte[32];
        is.read(serverDigest);

        socket.close();

        // Compare the message digests
        if (MessageDigest.isEqual(digest, serverDigest)) {
            System.out.println("Message digest verification successful.");
        } else {
            System.out.println("Message digest verification failed.");
        }
    }
}
