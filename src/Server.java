import java.net.*;
import java.io.*;
import java.util.*;

public class Server{

    static Server serverInstance;

    final static int PORT = 9000;
    private List<ClientHandler> clients;
    private ServerSocket serverSocket;
    private Socket socket;

    public List<ClientHandler> getClients() {
        return clients;
    }

    public void removeClient(ClientHandler client){
        String name = client.name;
        clients.remove(client);
        System.out.println(name + " has disconnected");
    }

    public ClientHandler findClient(String name){

        for (ClientHandler client : clients  ) {
            if(client.name.equals(name))
                return client;
        }
        return null;
    }

    private Server() throws IOException{
        //initializing server
        clients = new LinkedList<>();
        serverSocket = new ServerSocket(PORT);


    }

    public static Server getInstance() throws IOException{

        if (serverInstance == null)
            return new Server();
        else
            return serverInstance;
    }



    public static void main(String[] args) throws IOException{

        //waiting for clients
        Server server = Server.getInstance();

        while(true) {
            server.socket = server.serverSocket.accept();

            System.out.println("New client connected");

            ClientHandler client = new ClientHandler(server,server.socket);

            server.clients.add(client);

            client.start();
        }
    }

}
