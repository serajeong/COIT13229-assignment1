/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package a1.fitness;

/**

 * @author sera jeong 12211242
 * COIT13229 assignment 1
 * TCP connection 11xx = 1142 (because student number ends with 42)
 */

import java.net.*;
import java.io.*;
import java.util.*;
import a1.fitness.Member;
import java.nio.file.Files;


public class TCPServer {
    private static final int PORT_NO = 1142;
    private static final String FILE_MEMBER_LIST = "memberlist.txt";
    private static final String FILE_MEMBER_OBJECT = "memberlistObject";
    private static int mCounter = 1; //member number starting from 1

    public static void main(String[] args) throws IOException {
       
        //timer for saving member list objects
        TimerTask task = new TimerTask(){
            public void run(){
                if (new File(FILE_MEMBER_LIST).exists()){
                saveMemberObject();                    
                }

//                byte[] byteFile = null;
//                try{
//                    File file1 = new File(FILE_MEMBER_LIST);
//                    
//                    //server converts memberlist to memberobject if memberlist file exists
//                    if (file1.exists()){
//                        byteFile = Files.readAllBytes(file1.toPath());
//                        
//                            //TESTING with 'false' which rewrites object file every 2 seconds instead of 'true' that appends
//                            try(FileOutputStream fos = new FileOutputStream(FILE_MEMBER_OBJECT,false)){
//                                fos.write(byteFile);
//                            }catch(IOException e){
//                            }   
//                        }
//                    } catch (IOException e1){
//                        e1.printStackTrace();
//                    }
                }
            };
            Timer timer = new Timer("Timer");
            long delay = 0;
            long period = 2000; //every 2 seconds as required
            timer.scheduleAtFixedRate(task, delay, period);

            //create a server socket
            ServerSocket serverSocket = new ServerSocket(PORT_NO);
            System.out.println("TCPServer is running on port " + PORT_NO);

            Socket socket = serverSocket.accept();
//            System.out.println("Client connected: " + socket);

            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);
            OutputStream out = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);

                while(true) {
//                    int mNumber = mCounter++;
//                    System.out.println("Receiving data from client: " + mNumber);
                    InputStream is = socket.getInputStream();
                    byte[] bytes = new byte[1024];

                    int readByteCount = is.read(bytes);
//                    //FOR TESTING ONLY - DELETE BEFORE SUBMITTING
//                    System.out.println("readByteCount" + readByteCount);

                    if (readByteCount > 0){

//                        //FOR TESTING ONLY - DELETE BEFORE SUBMITTING
//                        System.out.println("Received data from Client");
                        int mNumber = mCounter++;
                        System.out.println("Receiving data from client: " + mNumber);
                    
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
                    //send feedback to client
                    dos.writeUTF("Save Data of the member number:");
                }
    }
    
//    Thread thread = new Thread(new Runnable(){            
    //save member details as text file
    private static void saveMemberList(Member member) {
        try (FileOutputStream fos = new FileOutputStream(FILE_MEMBER_LIST, true)) {
            String details = member.getFirstName() + ":" + member.getLastName() + ":" + member.getAddress() + ":" + member.getPhone() + "\n";
            fos.write(details.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //save member objects as object file
    private static void saveMemberObject() {
        try (FileInputStream fis = new FileInputStream(FILE_MEMBER_LIST);
             BufferedReader br = new BufferedReader(new InputStreamReader(fis));
             FileOutputStream fos = new FileOutputStream(FILE_MEMBER_OBJECT);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            //read text file, convert into member object and write object file
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                Member member = new Member(parts[0], parts[1], parts[2], parts[3]); //considering Member has 4 parameters
                oos.writeObject(member);
            }
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
