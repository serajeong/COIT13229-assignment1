/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package a1.fitness;

/**
 *
 * @author sera jeong 12211242
 * COIT13229 assignment 1
 * 
 */

import java.net.*;
import java.io.*;


public class TCPServer {
    private static final int PORT = 1142;
    private static final String memberList = "memberlist.txt";

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            // Create a server socket
            serverSocket = new ServerSocket(PORT);
            System.out.println("Receiving data from client: ");

            while (true) {
                // Wait for client connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // Create input and output streams for communication
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

                // Receive member details from client
                String firstName = dis.readUTF();
                String lastName = dis.readUTF();
                String address = dis.readUTF();
                String phone = dis.readUTF();

                // Save member details to file
                saveMemberDetails(firstName, lastName, address, phone);

                // Send feedback to client
                dos.writeUTF("Save Data of the member number:");

                // Close streams and socket
                dis.close();
                dos.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void saveMemberDetails(String firstName, String lastName, String address, String phone) {
        try (FileOutputStream fos = new FileOutputStream(memberList, true)) {
            String details = firstName + ", " + lastName + ", " + address + ", " + phone + "\n";
            fos.write(details.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
      
}
