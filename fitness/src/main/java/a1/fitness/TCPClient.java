/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package a1.fitness;

/**
 *
 * @author Sera Jeong 12211242
 */


import java.net.*;
import java.io.*;
import java.util.*;

public class TCPClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 1142;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             DataInputStream dis = new DataInputStream(socket.getInputStream());
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

            System.out.println("Enter your First Name:");
            String firstName = scanner.nextLine();
            System.out.println("Enter your Last Name:");
            String lastName = scanner.nextLine();            
            System.out.println("Enter your Address:");
            String address = scanner.nextLine();
            System.out.println("Enter your Phone Number:");
            String phone = scanner.nextLine();

            // Send member details to server
            dos.writeUTF(firstName);
            dos.writeUTF(lastName);            
            dos.writeUTF(address);
            dos.writeUTF(phone);

            // Receive feedback from server
            String feedback = dis.readUTF();
            System.out.println("Server response: " + feedback);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
