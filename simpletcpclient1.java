import java.io.*;
import java.net.*;

public class simpletcpclient1 {
    public static void main(String args[]) throws Exception {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", 6789);
  
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  
        System.out.print("Enter the mass of object 1: ");
        String mass1 = inFromUser.readLine();
        outToServer.writeBytes(mass1 + '\n');
  
        System.out.print("Enter the mass of object 2: ");
        String mass2 = inFromUser.readLine();
        outToServer.writeBytes(mass2 + '\n');
  
        System.out.print("Enter the distance between the two objects: ");
        String distance = inFromUser.readLine();
        outToServer.writeBytes(distance + '\n');
  
        String force = inFromServer.readLine();
        System.out.println("The gravitational force between the two objects is: " + force);
  
        clientSocket.close();
     }
}
