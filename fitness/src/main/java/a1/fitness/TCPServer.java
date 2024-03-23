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
import a1.fitness.Member;


public class TCPServer {
    private static final int portNo = 1142;
    private static final String memberList = "memberlist.txt";

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            // Create a server socket
            serverSocket = new ServerSocket(portNo);
            System.out.println("Receiving data from client: ");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

                //receive member details from client
                Member member = receiveMemberDetails(dis);
//                String firstName = dis.readUTF();
//                String lastName = dis.readUTF();
//                String address = dis.readUTF();
//                String phone = dis.readUTF();

                //save member details to txt file
                saveMemberDetails(member);

                //send feedback to client
                dos.writeUTF("Save Data of the member number:");
                dos.flush();
                

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
    //receive member details
    private static Member receiveMemberDetails(DataInputStream dis) throws IOException {
        String firstName = dis.readUTF();
        String lastName = dis.readUTF();
        String address = dis.readUTF();
        String phone = dis.readUTF();
        return new Member(firstName, lastName, address, phone);
    }

    // Method to save member details to a file
    private static void saveMemberDetails(Member member) {
        try (FileOutputStream fos = new FileOutputStream(memberList, true)) {
            String details = member.getFirstName() + ", " + member.getLastName() + ", " + member.getAddress() + ", " + member.getPhone() + "\n";
            fos.write(details.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
