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
            //FOR TESTING ONLY - DELETE BEFORE SUBMITTING
            System.out.println("data " + data);
            
            dos.write(data);
            dos.flush();
            byte[] recvBuffer = new byte[maxBufferSize];
            InputStream is = socket.getInputStream();
            int nReadSize = is.read(recvBuffer);
            
        
            System.out.println("Would you like to enter details for another member? Please type 'yes', if you do want to add details for multiple members now.");
            String endFlag = scanner.nextLine();

            if("no".equals(endFlag.toLowerCase())){
                break;
            }
        }
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
