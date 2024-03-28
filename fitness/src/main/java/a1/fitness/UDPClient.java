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

public class UDPClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT_NO = 2242;    
    
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket();

            byte[] requestData = "Requesting memberlistObject file".getBytes();

            //make request and send to server
            DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, InetAddress.getByName(SERVER_ADDRESS), PORT_NO);

            serverSocket.send(requestPacket);
            //FOR TESTING ONLY - DELETE BEFORE SUBMITTING            
            System.out.println("Request sent to server.");

            //receive response from server
            byte[] responseData = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length);
            serverSocket.receive(responsePacket);

            String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
            System.out.println("TESTING // CHANGE HEADINGS FOR THE TABLE");
            System.out.println("========================================"); 
            System.out.println(response);

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}