import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

    final static String IP = "127.0.0.1";
    final static int PORT = 5000;
    static Scanner scn;
    static Socket socket;
    static ObjectOutputStream output;
    static ObjectInputStream input;

    public static void disconnect() throws IOException{

        Message logOut = new Message("logOut");
        output.writeObject(logOut);

        output.close();
        input.close();
        socket.close();
        scn.close();

    }

    public static void main(String[] args) throws IOException{

        scn = new Scanner(System.in);

        System.out.println("Connecting to server");
        socket = new Socket(IP,PORT);
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        System.out.println("Connected");

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
                        output.writeObject(new Message(line));
                    }
                }

            }
        });
        //TODO: fix read message from obeject stream
        Thread receiveMsg = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    try {
                        Message msg = input.readObject();
                        System.out.println(msg.toString());
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
        sendMsg.start();

    }
}
