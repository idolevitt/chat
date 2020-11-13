import message.*;

import java.net.*;
import java.io.*;
import java.util.*;

public class ClientHandler extends Thread{

    Server server;
    Socket socket;
    ObjectOutputStream output;
    DataInputStream dis;
    Boolean isLoggedIn;
    String name;
    Date date;


    /**
     * constructor
     *
     * @param socket
     */
    ClientHandler(Server server, Socket socket){

        this.server = server;
        this.socket = socket;
        date = new Date();

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            output.writeObject(new CustomMessage("welcome! what's your name?"));
            name = dis.readUTF();
            while(name.equals("all") || server.findClient(name) != null){
                output.writeObject(new CustomMessage("Name taken / invalid, pick another name"));
                name = dis.readUTF();
            }
            output.writeObject(new CustomMessage("Hey " + name + "! Welcome to the server"));
        }
        catch (IOException i){
            i.printStackTrace();
        }

        isLoggedIn = true;
    }

    @Override
    public void run() {

        String msg;

        while(isLoggedIn){

            try{
                msg = dis.readUTF();
                classification(msg);

            }
            catch (IOException i){
                try {
                    disconnect();
                } catch (IOException e) { }
                
            }
        }
    }

    /**
     * Classify the type of message and sends it to its recipient
     *
     * @param msg
     * @throws IOException
     */
    public void classification(String msg) throws IOException{

        String recipientName;
        String text;
        ClientHandler recipient;

        if(msg.equals("logout")){
            disconnect();
        }
        else if (msg.equals("/time"))
            time();
        else if (msg.equals("/help"))
            output.writeObject(new HelpMessage());
        else if (msg.equals("/online"))
            online();
        else if (msg.endsWith("@all"))
            broadcast(msg.substring(0,msg.length() - "@all".length()));
        else if (msg.lastIndexOf("@") == -1) {
            error();
        }
        else {
            recipientName = msg.substring(msg.lastIndexOf("@") + 1);
            text = msg.substring(0, msg.lastIndexOf("@"));
            recipient = server.findClient(recipientName);
            System.out.println("before: " + recipientName);
            if (recipient == null) {
                output.writeObject(new ErrorMessage());
                System.out.println("if");
            }
            else {
                sendMessage(text, recipient);
                System.out.println("else");
            }


        }

    }

    /**
     * disconnect the current client
     *
     */
    private void disconnect() throws IOException{
        isLoggedIn = false;
        output.close();
        dis.close();
        socket.close();

        server.removeClient(this);
    }

    /**
     * Returns a string with all the online clients.
     *
     * @throws IOException
     */
    public void online() throws IOException{

        List<String> clientsNames = new LinkedList<>();

        for (ClientHandler client : server.getClients()) {
            if (client.isLoggedIn){
                clientsNames.add(client.name);
            }
        }

        System.out.println("sending " + name + " a list of all the online clients");
        output.writeObject(new OnlineClientsMessage(clientsNames));
    }

    /**
     * Sends a broadcast message to all clients
     *
     * @param text
     * @throws IOException
     */
    public void broadcast(String text) throws IOException {
        for (ClientHandler client : server.getClients()) {
            if (!client.name.equals(this.name))
                sendMessage(text, client);
        }
    }

    /**
     * Sends the current time to the client
     *
     * @throws IOException
     */
    public void time()throws IOException{
        output.writeObject(new DateMessage());
    }

    /**
     * Sends a private message
     *
     * @param text
     * @param recipient
     * @throws IOException
     */
    public void sendMessage(String text, ClientHandler recipient) throws IOException{

        recipient.output.writeObject(new PrivateMessage(this.name, text));
    }


    /**
     * Sends an error message to the client
     *
     * @throws IOException
     */
    public void error() throws IOException{

        output.writeObject(new ErrorMessage());
    }

}
