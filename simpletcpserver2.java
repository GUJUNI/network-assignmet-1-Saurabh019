// write a java TCP program for  both client and server to prove  motion of a satellite around a planet be 
// considered simple harmonic. 

import java.io.*;
import java.net.*;

public class TCPServer {
   public static void main(String args[]) throws Exception {
      ServerSocket welcomeSocket = new ServerSocket(6789);

      while (true) {
         Socket connectionSocket = welcomeSocket.accept();

         BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
         DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

         double G = 6.6743 * Math.pow(10, -11);
         String distance = inFromClient.readLine();
         String planetMass = inFromClient.readLine();
         String satelliteMass = inFromClient.readLine();

         double r = Double.parseDouble(distance);
         double M = Double.parseDouble(planetMass);
         double m = Double.parseDouble(satelliteMass);

         double period = 2 * Math.PI * Math.sqrt(Math.pow(r, 3) / (G * (M + m)));

         outToClient.writeBytes(String.valueOf(period) + '\n');
      }
   }
}
