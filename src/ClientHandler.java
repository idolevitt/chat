import java.net.*;
import java.io.*;
import java.util.*;

public class ClientHandler extends Thread{

    Socket socket;
    ObjectOutputStream output;
    ObjectInputStream input;
    Boolean isLoggedIn;
    String name;
    Date date;

    //constructor
    ClientHandler(Socket socket){

        this.socket = socket;
        date = new Date();

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            output.writeObject("Welcome! what's your name?");
            name = input.readObject().toString();
        }
        catch (IOException i){
            i.printStackTrace();
        }
        catch (ClassNotFoundException c){
            System.out.println("ERROR: class not Found");
        }

        isLoggedIn = true;
    }

    //returns a string with all the online clients
    public void online(List<ClientHandler> clients){

        String onlineClients = "";
        int count = 0;
        for(ClientHandler client : clients){
            if(count == 3){
                onlineClients += "\n";
                count = 0;
            }
            onlineClients += client + "\t";
            count++;
        }
        System.out.println("sending " + name + " a list of all the online clients");
        output.writeObject(Message(onlineClients));
    }

    //sends a broadcast message to all clients
    public void broadcast(Message msg, List<ClientHandler> clients) throws IOException{
        for(ClientHandler client : clients){
            if(!client.name.equals(this.name))
                client.output.writeObject(msg);
        }
    }

    //sends the current time to the client
    public void time(Date date)throws IOException{
        output.writeObject(Message(date.toString()));
    }

    public void sendMessage(Message msg){
        String recipient;
    }

    public void messageHandler(Message msg){

    }

}
