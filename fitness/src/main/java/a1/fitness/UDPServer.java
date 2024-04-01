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
            System.out.println("UDPServer is running on port " + PORT_NO);
            
            byte[] buffer = new byte[1024];
            
            DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(requestPacket);
            String request = new String(requestPacket.getData(), 0, requestPacket.getLength());       
            System.out.println("Client Request: " + request);

            //read object file that TCPServer created
             FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            
            //do while loop that will convert member object back to string
             Member member = null;
             int i = 1;
             String returnDetails = "";
             try {
                do  {
                  member = (Member) objectInputStream.readObject();
                    //padding applied to display in table format as required
                    if (i > 1) {
                        returnDetails += String.format("\n| %-13s | %-13s | %-20s | %-12s |",
                            member.getFirstName(), member.getLastName(), member.getAddress(), member.getPhone());
                    } else {
                        returnDetails += String.format("| %-13s | %-13s | %-20s | %-12s |",
                            member.getFirstName(), member.getLastName(), member.getAddress(), member.getPhone());
                    }                       
                   i++;
                } while (member.getFirstName() != null);
             } catch (IOException e) {
                 
             }
             objectInputStream.close();

            //send response to client 
             byte[] responseData = returnDetails.getBytes();
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