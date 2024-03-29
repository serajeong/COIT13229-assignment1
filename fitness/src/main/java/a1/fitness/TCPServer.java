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
import java.nio.file.Files;


public class TCPServer {
    private static final int PORT_NO = 1142;
    private static final String FILE_MEMBER_LIST = "memberlist.txt";
    private static final String FILE_MEMBER_OBJECT = "memberlistObject.txt";

    public static void main(String[] args) throws IOException {
        
        
        //timer for saving member list objects
        TimerTask task = new TimerTask(){
            public void run(){
                byte[] byteFile = null;
                try{
                    File file1 = new File(FILE_MEMBER_LIST);
                    
                    //server convers memberlist to memberobject if memberlist file exists
                    if (file1.exists()){
                        byteFile = Files.readAllBytes(file1.toPath());
                        
                            try(FileOutputStream fos = new FileOutputStream(FILE_MEMBER_OBJECT,true)){
                                fos.write(byteFile);
                            }catch(IOException e){
                            }   
                        }
                    } catch (IOException e1){
                        e1.printStackTrace();
                    }
                }
            };
            Timer timer = new Timer("Timer");
            long delay = 0;
            long period = 2000; //2 seconds as required
            timer.scheduleAtFixedRate(task, delay, period);



            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(PORT_NO);
            System.out.println("Receiving data from client: ");


            Socket socket = serverSocket.accept();
            System.out.println("Client connected: " + socket);

            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);
            OutputStream out = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);


                while(true) {
                    InputStream is = socket.getInputStream();
                    byte[] bytes = new byte[1024];

                    int readByteCount = is.read(bytes);
                    //FOR TESTING ONLY - DELETE BEFORE SUBMITTING
                    System.out.println("readByteCount" + readByteCount);

                    if (readByteCount > 0){

                        //FOR TESTING ONLY - DELETE BEFORE SUBMITTING
                        System.out.println("Received data from Client");

                        Member receiveMember = toObject(bytes, Member.class);
                        saveMemberList(receiveMember);
                        sendData(bytes, socket);
                    } else {
                        //closing
                        in.close();
                        out.close();
                        socket.close();
                        serverSocket.close();
                        break;
                    }
                }
    }

                
    // save member details to a file
    private static void saveMemberList(Member member) {
        try (FileOutputStream fos = new FileOutputStream(FILE_MEMBER_LIST, true)) {
            String details = member.getFirstName() + ":" + member.getLastName() + ":" + member.getAddress() + ":" + member.getPhone() + "\n";
            fos.write(details.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //byte to object
    public static <T> T toObject(byte[] bytes, Class<T> type){
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream (bis);
            obj = ois.readObject();
        } catch (IOException ex){
            ex.printStackTrace();
        } catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        return type.cast(obj);
    }
    
    public static void sendData(byte[] bytes, Socket socket){
        try{
            OutputStream os = socket.getOutputStream();
            os.write(bytes);
            os.flush();            
        } catch(Exception e1){
            e1.printStackTrace();
        }
    }
    
}
