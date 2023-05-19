import java.net.*;
import java.io.*;

public class UDPClient {
    public static void main(String args[]) {
        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket();

            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            InetAddress IPAddress = InetAddress.getByName("localhost");
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            System.out.print("Enter number of particles: ");
            int numParticles = Integer.parseInt(inFromUser.readLine());

            String input = "";

            for (int i = 0; i < numParticles; i++) {
                System.out.print("Enter mass of particle " + (i + 1) + ": ");
                double mass = Double.parseDouble(inFromUser.readLine());

                System.out.print("Enter x-coordinate of particle " + (i + 1) + ": ");
                double x = Double.parseDouble(inFromUser.readLine());

                System.out.print("Enter y-coordinate of particle " + (i + 1) + ": ");
                double y = Double.parseDouble(inFromUser.readLine());

                System.out.print("Enter z-coordinate of particle " + (i + 1) + ": ");
                double z = Double.parseDouble(inFromUser.readLine());

                input += mass + "," + x + "," + y + "," + z + ",";
            }

            sendData = input.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAddress, 9876);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String output = new String(receivePacket.getData()).trim();
            System.out.println(output);
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (clientSocket != null) {
            clientSocket.close();
        }
    }
}
