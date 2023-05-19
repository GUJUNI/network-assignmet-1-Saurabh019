//multithreaded UDP client and server program to calculate the kinetic energy 
//of a ball free falling towards the poles in java

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

            String initialVelocityStr = new String(receivePacket.getData()).trim();
            double initialVelocity = Double.parseDouble(initialVelocityStr);

            double mass = 1.0; // Mass of the ball
            double gravity = 9.8; // Acceleration due to gravity

            double kineticEnergy = 0.5 * mass * Math.pow(initialVelocity, 2);

            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();

            byte[] sendData = String.valueOf(kineticEnergy).getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            serverSocket.send(sendPacket);

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
