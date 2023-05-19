import java.io.*;
import java.net.*;

public class TCPClient {
   public static void main(String args[]) throws Exception {
      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
      Socket clientSocket = new Socket("localhost", 6789);

      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      System.out.print("Enter the distance between the planet and the satellite: ");
      String distance = inFromUser.readLine();
      outToServer.writeBytes(distance + '\n');

      System.out.print("Enter the mass of the planet: ");
      String planetMass = inFromUser.readLine();
      outToServer.writeBytes(planetMass + '\n');

      System.out.print("Enter the mass of the satellite: ");
      String satelliteMass = inFromUser.readLine();
      outToServer.writeBytes(satelliteMass + '\n');

      String period = inFromServer.readLine();
      System.out.println("The period of the satellite's motion is: " + period);

      clientSocket.close();
   }
}
