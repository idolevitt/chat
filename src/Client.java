import message.Message;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

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

        String ip;
        int port;
        System.out.println("Welcome! please fill in the following:");

        while (true) {
            try {
                System.out.print("Server IP address:");
                ip = scn.nextLine();
                System.out.print("Port:");
                port = Integer.parseInt(scn.nextLine());
                System.out.println("Connecting to server");
                socket = new Socket(ip, port);
                break;
            } catch (NumberFormatException n) {
                System.out.println("Invalid input try again:\n");
            } catch (SocketException s){
                System.out.print("Server is unreachable\n");
            } catch (UnknownHostException u){
                System.out.print("Server is unreachable\n");
            }
        }


        dos = new DataOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        System.out.println("Connected");

        /**
         * Sending messages to the server
         */
        Thread sendMsg = new Thread(() -> {
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

        });



        /**
         * Waiting for messages from the server
         */
        Thread receiveMsg = new Thread(() -> {

            while (true){
                try {
                    Message msg = (Message) input.readObject();
                    msg.handleMessage();
                }
                catch (IOException i){
                    System.out.println("No connection with the server");
                    break;
                }
                catch (ClassNotFoundException c){
                    System.out.println("class");
                    c.printStackTrace();
                }

            }

        });

        sendMsg.start();
        receiveMsg.start();

    }

}
