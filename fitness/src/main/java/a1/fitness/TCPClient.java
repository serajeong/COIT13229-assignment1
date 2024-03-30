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
import java.util.regex.Pattern;

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
        Pattern numericPattern = Pattern.compile("\\d+");
        
        while(true){
            //ask user to enter details for members
//            System.out.println("Enter your First Name:");
//            String firstName = scanner.nextLine();
            String firstName;
            //validate first name is entered
            do {
                System.out.println("Enter your First Name:");
                firstName = scanner.nextLine();
                if (firstName == null || firstName.trim().isEmpty()) {
                    System.out.println("Error - empty input: Please enter valid input for first name.");
                }
            } while (firstName == null || firstName.trim().isEmpty());
          
            
//            System.out.println("Enter your Last Name:");
//            String lastName = scanner.nextLine();     
            String lastName;
            //validate last name is entered
            do {
                System.out.println("Enter your Last Name:");
                lastName = scanner.nextLine();   
                if (lastName == null || lastName.trim().isEmpty()) {
                    System.out.println("Error - empty input: Please enter valid input for last name.");
                }
            } while (lastName == null || lastName.trim().isEmpty());            
            
//            System.out.println("Enter your Address:");
//            String address = scanner.nextLine();
            String address;
            //validate last name is entered
            do {
                System.out.println("Enter your Address:");
                address = scanner.nextLine();
                if (address == null || address.trim().isEmpty()) {
                    System.out.println("Error - empty input: Please enter valid input for address.");
                }
            } while (address == null || address.trim().isEmpty());                  
            
//            System.out.println("Enter your Phone Number:");
//            String phone = scanner.nextLine();      
            String phone;
            //validate phone number is entered
            //validate phon number is 10 digit nubmer
            do {
                System.out.println("Enter your Phone Number:");
                phone = scanner.nextLine();
                if (phone == null || phone.trim().isEmpty()) {
                    System.out.println("Error - empty input: Please enter valid input for phone number");
                } else if (!numericPattern.matcher(phone).matches() || phone.length() != 10) {
                    System.out.println("Error - invalid input: Please enter 10 digit numbers for phone number.");
                }
            } while (phone == null || phone.trim().isEmpty() || !numericPattern.matcher(phone).matches() || phone.length() != 10);

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
