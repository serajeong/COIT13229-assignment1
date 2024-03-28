/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package a1.fitness;
/**
 *
 * @author sera jeong 12211242
 * COIT13229 assignment 1
 * UDP connection 22xx = 2242 (because student number ends with 42)
 */
import java.net.*;
import java.io.*;
import a1.fitness.Member;
import java.util.ArrayList;

public class UDPServer {
    private static final int PORT_NO = 2242;    
    private static final String FILE_NAME = "memberlistObject";

    public static void main(String[] args) throws ClassNotFoundException {
        try {
            DatagramSocket socket = new DatagramSocket(PORT_NO);
            System.out.println("Server is running on port " + PORT_NO);
            
            byte[] buffer = new byte[1024];

            DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);

            socket.receive(requestPacket);
            //FOR TESTING ONLY - DELETE BEFORE SUBMITTING            
            System.out.println("Request received from client.");

            String request = new String(requestPacket.getData(), 0, requestPacket.getLength());
            //FOR TESTING ONLY - CHANGE MESSAGE BEFORE SUBMITTING            
            System.out.println("Request message: " + request);

            // read object file
             FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//             ArrayList<Member> memberList  = (ArrayList<Member>) objectInputStream.readObject();            
//             Member member = (Member) objectInputStream.readObject();
//             System.out.println("First name :"  + member.getFirstName());
//             System.out.println("Last name :"  + member.getLastName());
//             System.out.println("Address size :"  + member.getAddress());
//             System.out.println("Phone :"  + member.getPhone());

             Member member = null;
             int i = 1;
             String returnRow = "";
             try {
                do  {
                  member = (Member) objectInputStream.readObject();
//                   System.out.println("First name :"  + member.getFirstName());
//                   System.out.println("Last name :"  + member.getLastName());
//                   System.out.println("Address size :"  + member.getAddress());
//                   System.out.println("Phone :"  + member.getPhone());
                   if ( i > 1 ) {
                       returnRow +=  "\r\n" + member.getFirstName() + ", " + member.getLastName()+ ", "  +  member.getAddress()+ ", "  + member.getPhone();
                   } else {
                       returnRow +=  member.getFirstName()+ ", "  + member.getLastName() + ", " +  member.getAddress() + ", " + member.getPhone();
                   }
                   
                   i++;
                } while (member.getFirstName() != null);
             } catch (IOException e) {
                 
             }
             objectInputStream.close();

            //FOR TESTING ONLY - DELETE BEFORE SUBMITTING             
           System.out.println("returnRow :"  + returnRow);
            //send resdponse to client 
             //String responseMessage = "Object file read successfully [" + returnRow +"]";
             byte[] responseData = returnRow.getBytes();
             InetAddress clientAddress = requestPacket.getAddress();
             int clientPort = requestPacket.getPort();
             DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
             socket.send(responsePacket);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}