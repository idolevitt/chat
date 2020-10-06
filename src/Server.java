import java.net.*;
import java.io.*;
import java.util.*;

public class Server{

    final static int PORT = 5000;
    private List<ClientHandler> clients = new LinkedList<>();

    public List<ClientHandler> getClients() {
        return clients;
    }

    public void removeClient(ClientHandler client){
        String name = client.name;
        clients.remove(client);
        System.out.println(name + " disconnected");
    }

    public ClientHandler findClient(String name){

        for (ClientHandler client : clients  ) {
            if(client.name.equals(name))
                return client;
        }
        return null;
    }



    public void main(String[] args) throws IOException{
        //initializing server
        ServerSocket server = new ServerSocket(PORT);

        Socket socket;

        //waiting for clients
        while(true) {
            socket = server.accept();

            System.out.println("New client connected");

            ClientHandler client = new ClientHandler(socket);

            clients.add(client);

            client.start();
        }
    }

}
