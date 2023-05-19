import java.io.*;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) {
        DatagramSocket clientSocket;
        InetAddress serverAddress;
        int serverPort = 9876;

        try {
            clientSocket = new DatagramSocket();
            serverAddress = InetAddress.getByName("localhost");

            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter initial velocity of the ball: ");
            double initialVelocity = Double.parseDouble(inFromUser.readLine());

            byte[] sendData = String.valueOf(initialVelocity).getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            clientSocket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String response = new String(receivePacket.getData()).trim();
            System.out.println("Kinetic Energy: " + response);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
