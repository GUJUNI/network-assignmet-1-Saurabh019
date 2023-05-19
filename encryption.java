//java server and client program that performs encryption/decryption


// server code:

import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Server started. Listening on port 8000...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

            // Generate a secret key for encryption/decryption
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128); // Using AES with 128-bit key
            SecretKey secretKey = keyGenerator.generateKey();

            // Get the client's input stream for reading the message
            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            String message = (String) objectInputStream.readObject();

            // Encrypt the message
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedMessage = cipher.doFinal(message.getBytes());

            // Send the encrypted message and the secret key back to the client
            OutputStream outputStream = clientSocket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(encryptedMessage);
            objectOutputStream.writeObject(secretKey);

            clientSocket.close();
            System.out.println("Client disconnected.");
        }
    }
}


//client code:

import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java Client <server>");
            return;
        }

        String serverName = args[0];
        int serverPort = 8000;

        // Get the message from the user
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter a message: ");
        String message = reader.readLine();

        // Connect to the server
        Socket socket = new Socket(serverName, serverPort);

        // Send the message to the server
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(message);

        // Receive the encrypted message and the secret key from the server
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        byte[] encryptedMessage = (byte[]) objectInputStream.readObject();
        SecretKey secretKey = (SecretKey) objectInputStream.readObject();

        // Decrypt the message
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedMessage = cipher.doFinal(encryptedMessage);

        // Display the decrypted message
        String decryptedText = new String(decryptedMessage);
        System.out.println("Decrypted message: " + decryptedText);

        socket.close();
    }
}
