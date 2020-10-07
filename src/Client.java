import message.DateMessage;
import message.Message;
import message.OnlineClientsMessage;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

    final static String IP = "127.0.0.1";
    final static int PORT = 5000;
    static Scanner scn;
    static Socket socket;
    static DataOutputStream dos;
    static ObjectInputStream input;

    /**
     * Disconnects the client and informs the server
     *
     * @throws IOException
     */
    public static void disconnect() throws IOException{

        dos.writeUTF("logout");

        dos.close();
        input.close();
        socket.close();
        scn.close();

    }


    public static void main(String[] args) throws IOException{

        scn = new Scanner(System.in);

        System.out.println("Connecting to server");
        socket = new Socket(IP,PORT);
        dos = new DataOutputStream(socket.getOutputStream());
        System.out.println("before");
        input = new ObjectInputStream(socket.getInputStream());
        System.out.println("after");
        System.out.println("Connected");

        /**
         * Sending messages to the server
         */
        Thread sendMsg = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

                    String line = scn.nextLine();
                    if(line.toLowerCase().equals("log out")||
                            line.toLowerCase().equals("disconnect")){
                        try {
                            disconnect();
                        }
                        catch (IOException i){
                            i.printStackTrace();
                        }
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

            }
        });



        /**
         * Waiting for messages from the server
         */
        Thread receiveMsg = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    try {
                        Message msg = (Message) input.readObject();
                        msg.handleMessage();
                    }
                    catch (IOException i){
                        i.printStackTrace();
                    }
                    catch (ClassNotFoundException c){
                        c.printStackTrace();
                    }

                }

            }
        });

        sendMsg.start();
        receiveMsg.start();

    }

}
