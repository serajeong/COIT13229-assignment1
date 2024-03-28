/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package a1.fitness;

/**
 *
 * @author sera jeong 12211242
 * COIT13229 assignment 1
 * TCP connection 11xx = 1142 (because student number ends with 42)
 */

import java.net.*;
import java.io.*;
import java.util.*;
import a1.fitness.Member;

public class TCPClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT_NO = 1142;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_ADDRESS, PORT_NO);
        
        InputStream in = socket.getInputStream();
        DataInputStream dis = new DataInputStream(in);
        OutputStream out = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out);        
        
        Scanner scanner = new Scanner(System.in);
        int maxBufferSize = 1024;
        
        while(true){
            //ask user to enter details for members
            System.out.println("Enter your First Name:");
            String firstName = scanner.nextLine();
            System.out.println("Enter your Last Name:");
            String lastName = scanner.nextLine();            
            System.out.println("Enter your Address:");
            String address = scanner.nextLine();
            System.out.println("Enter your Phone Number:");
            String phone = scanner.nextLine();      
            
            Member member = new Member(firstName, lastName, address, phone);
            String msg = "Sending Data to Server................\n" + firstName + ":" +  lastName +  ":" +  address +  ":" +  phone; 
            System.out.println(msg);

            byte[] data = toByteArray(member);
//            //FOR TESTING ONLY - DELETE BEFORE SUBMITTING
//            System.out.println("data " + data);
            
            dos.write(data);
            dos.flush();
            byte[] recvBuffer = new byte[maxBufferSize];
            InputStream is = socket.getInputStream();
            int nReadSize = is.read(recvBuffer);
            
            //receive feedback from server
            String feedback = dis.readUTF();
            System.out.println("Server response: " + feedback);
            
            //ask member if they want to add more member
            System.out.println("Would you like to enter more member details? Please type 'no' to exit, otherwise it will continue.");
            String endFlag = scanner.nextLine();
            
            //only break if user decides to exit by typing 'no' as prompted in message
            if("no".equals(endFlag.toLowerCase())){
                break;
            }
        }
        //closing
        dos.close();
        dis.close();
        in.close();
        out.close();
        socket.close();
    }
    
    //byte to object
    public static <T> T toObject(byte[] bytes, Class<T> type){
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream (bis);
            obj = ois.readObject();
        } catch (IOException ex){
        } catch (ClassNotFoundException ex){
        }
        return type.cast(obj);
    }
    
    //object to byte
    public static byte[] toByteArray (Object obj) {
    	byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
            bos.close();
            bytes = bos.toByteArray();
        } catch (IOException ex) {
        }
        return bytes;
    }
}
