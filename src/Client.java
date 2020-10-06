import message.*;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

    final static String IP = "109.186.178.254";
    final static int PORT = 9000;
    static Scanner scn;
    static Socket socket;
    static DataOutputStream dos;
    static ObjectInputStream input;
    static Thread sendMsg;
    static Thread receiveMsg;
    static Boolean isOnline;

    /**
     * Disconnects the client and informs the server
     *
     * @throws IOException
     */


    public static void main(String[] args) throws IOException{

        scn = new Scanner(System.in);

        System.out.println("Connecting to server");
        socket = new Socket(IP,PORT);
        dos = new DataOutputStream(socket.getOutputStream());
        System.out.println("before");
        input = new ObjectInputStream(socket.getInputStream());
        System.out.println("after");
        System.out.println("Connected");
        isOnline = true;

        /**
         * Sending messages to the server
         */
        sendMsg = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isOnline){

                    String line = scn.nextLine();
                    if(line.toLowerCase().equals("log out")||
                            line.toLowerCase().equals("disconnect")){
                        isOnline = false;

                    }
                    else{
                        try{
                            dos.writeUTF(line);
                        }
                        catch (IOException i){
                            i.printStackTrace();
                        }

                    }
                }
                try {
                    dos.writeUTF("logout");
                    dos.close();
                    scn.close();
                    System.out.println("logged out");
                }catch (IOException i){
                    i.printStackTrace();
                }
            }
        });




        /**
         * Waiting for messages from the server
         */
        receiveMsg = new Thread(new Runnable() {
            @Override
            public void run() {

                while (isOnline){
                    try {
                        Message msg = (Message) input.readObject();
                        msg.handleMessage();
                    }
                    catch (IOException i){
                        //i.printStackTrace();
                    }
                    catch (ClassNotFoundException c){
                        c.printStackTrace();
                    }

                }
                try {
                    input.close();
                    socket.close();
                }catch (IOException i){
                    i.printStackTrace();
                }

            }
        });

        sendMsg.start();
        receiveMsg.start();

    }

}
