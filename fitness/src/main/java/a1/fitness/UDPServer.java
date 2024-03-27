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
            System.out.println("Request received from client.");

            String request = new String(requestPacket.getData(), 0, requestPacket.getLength());
            System.out.println("Request message: " + request);

            // read object file
             FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
             Member member = (Member) objectInputStream.readObject();
             objectInputStream.close();

            //send resdponse to client
             String responseMessage = "Object file read successfully";
             byte[] responseData = responseMessage.getBytes();
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