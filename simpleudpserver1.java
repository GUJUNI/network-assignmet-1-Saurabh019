//write a java UDP program for  both client and server to find centre of mass of a system of particles.

import java.net.*;
import java.io.*;

public class UDPServer {
    public static void main(String args[]) {
        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket(9876);

            while (true) {
                byte[] receiveData = new byte[1024];
                byte[] sendData = new byte[1024];

                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String input = new String(receivePacket.getData()).trim();

                String[] inputArr = input.split(",");

                double totalMass = 0;
                double totalX = 0;
                double totalY = 0;
                double totalZ = 0;

                for (int i = 0; i < inputArr.length; i += 4) {
                    double mass = Double.parseDouble(inputArr[i]);
                    double x = Double.parseDouble(inputArr[i + 1]);
                    double y = Double.parseDouble(inputArr[i + 2]);
                    double z = Double.parseDouble(inputArr[i + 3]);

                    totalMass += mass;
                    totalX += mass * x;
                    totalY += mass * y;
                    totalZ += mass * z;
                }

                double centerX = totalX / totalMass;
                double centerY = totalY / totalMass;
                double centerZ = totalZ / totalMass;

                String output = String.format("Center of mass: (%.2f, %.2f, %.2f)", centerX, centerY, centerZ);
                sendData = output.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}
