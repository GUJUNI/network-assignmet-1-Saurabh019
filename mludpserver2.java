//multithreaded udp server and client program to calculate the days left until haley's 
//comet comes close to earth in java
 
import java.io.*;
import java.net.*;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket serverSocket;
        int serverPort = 9876;

        try {
            serverSocket = new DatagramSocket(serverPort);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            // Perform your calculations to determine the days left until Haley's Comet comes close to Earth
            // You can use the current date and the orbital parameters of the comet for this calculation

            String daysLeft = "42"; // Placeholder value, replace it with the actual calculated days

            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();

            byte[] sendData = daysLeft.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            serverSocket.send(sendPacket);

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();    
        }
    }
}
