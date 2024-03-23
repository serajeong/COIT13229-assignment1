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
import java.util.*;
import a1.fitness.Member;

public class TCPClient {
    private static final String serverAddress = "localhost";
    private static final int portNo = 1142;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        try (
             
                Socket socket = new Socket(serverAddress, portNo);
             
                DataInputStream dis = new DataInputStream(socket.getInputStream());
             
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
            
            String answer = "";
            do{
                System.out.println("Enter your First Name:");
                String firstName = scanner.nextLine();
                System.out.println("Enter your Last Name:");
                String lastName = scanner.nextLine();            
                System.out.println("Enter your Address:");
                String address = scanner.nextLine();
                System.out.println("Enter your Phone Number:");
                String phone = scanner.nextLine();
                String msg = "Sending Data to Server................\n" + firstName + ":" +  lastName +  ":" +  address +  ":" +  phone; 
                System.out.println(msg);
                               
                //send member details to server
                Member member = new Member(firstName, lastName, address, phone);
                sendMemberDetails(member, dos);
//                dos.writeUTF(firstName);
//                dos.writeUTF(lastName);            
//                dos.writeUTF(address);
//                dos.writeUTF(phone);
////               
//                System.out.println("msg" + msg);
//                

                //receive feedback from server
                String feedback = dis.readUTF();
                System.out.println("Server response: " + feedback);
            
                System.out.println("Would you like to enter details for another member? Please type 'yes', if you do want to add details for multiple members now.");
                answer = scanner.nextLine();
                
                System.out.println("answer" + answer);
            } while(answer.equalsIgnoreCase("yes"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    //send member detials to server method
    private static void sendMemberDetails(Member member, DataOutputStream dos) throws IOException {
        dos.writeUTF(member.getFirstName());
        dos.writeUTF(member.getLastName());
        dos.writeUTF(member.getAddress());
        dos.writeUTF(member.getPhone());
    }
}