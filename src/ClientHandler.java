import message.*;

import java.net.*;
import java.io.*;
import java.util.*;

public class ClientHandler extends Thread{

    Socket socket;
    ObjectOutputStream output;
    DataInputStream dis;
    Boolean isLoggedIn;
    String name;
    Date date;

    //TODO: message.Message class.

    /**
     * constructor
     *
     * @param socket
     */
    ClientHandler(Socket socket){

        this.socket = socket;
        date = new Date();

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            output.writeObject(new WelcomeMessage());
            name = dis.readUTF();
        }
        catch (IOException i){
            i.printStackTrace();
        }

        isLoggedIn = true;
    }

    @Override
    public void run() {

        String msg;

        while(true){

            try{
                msg = dis.readUTF();
                classification(msg);

            }
            catch (IOException i){
                i.printStackTrace();
            }
        }
    }

    public void classification(String msg) throws IOException{

        String recipientName;
        String text;
        ClientHandler recipient;

        if (msg.equals("/time"))
            time();
        if (msg.equals("/online"))
            online();
        if (msg.endsWith("@all"))
            broadcast(msg.substring(0,msg.length() - "@all".length()));
        if (msg.lastIndexOf("@") == -1)
            error();
        else {
            recipientName = msg.substring(msg.lastIndexOf("@"));
            text = msg.substring(0, msg.lastIndexOf("@"));
            recipient = Server.findClient(recipientName);
            if (recipient == null)
                output.writeObject(new ErrorMessage());
            else
                sendMessage(text, recipient);



        }

    }

    /**
     * Returns a string with all the online clients.
     *
     * @throws IOException
     */
    public void online() throws IOException{

        List<String> clientsNames = new LinkedList<>();

        for (ClientHandler client : Server.getClients()) {
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
        for (ClientHandler client : Server.getClients()) {
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
