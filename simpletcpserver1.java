//write a java TCP program for  both client and server to prove Why weight of a body becomes zero at the centre of the earth.

import java.io.*; 
import java.net.*;

public class simpletcpserver1 {
    public static void main(String args[]) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(6789);
  
        while (true) {
           Socket connectionSocket = welcomeSocket.accept();
  
           BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
           DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
  
           double G = 6.6743 * Math.pow(10, -11);
           String mass1 = inFromClient.readLine();
           String mass2 = inFromClient.readLine();
           String distance = inFromClient.readLine();
  
           double m1 = Double.parseDouble(mass1);
           double m2 = Double.parseDouble(mass2);
           double r = Double.parseDouble(distance);
  
           double force = G * ((m1 * m2) / (r * r));
  
           outToClient.writeBytes(String.valueOf(force) + '\n');
        }
     }
}
