import java.net.*;
import java.io.*;
import java.util.*;

public class Server{

    static Server serverInstance;

    final static int PORT = 9000;
    private List<ClientHandler> clients;
    private ServerSocket serverSocket;
    private Socket socket;


    /**
     * Private constructor
     * @throws IOException
     */
    private Server() throws IOException{
        //initializing server
        clients = new LinkedList<>();
        serverSocket = new ServerSocket(PORT);
    }


    /**
     * Singleton constructor
     * @return Server instance
     * @throws IOException
     */
    public static Server getInstance() throws IOException{

        if (serverInstance == null)
            return new Server();
        else
            return serverInstance;
    }


    /**
     * @return a list of all the clients
     */
    public List<ClientHandler> getClients() {
        return clients;
    }


    /**
     * Removing the client from the server's active clients list
     *
     * @param client
     */
    public void removeClient(ClientHandler client){
        String name = client.name;
        clients.remove(client);
        System.out.println(name + " disconnected");
    }


    /**
     * Searches for a client and returns it ClientHandler
     * or null if no such client exists
     * @param name
     * @return Client handler of the given name/ null
     * if no such client exists
     */
    public ClientHandler findClient(String name){

        for (ClientHandler client : clients  ) {
            if(client.name.equals(name))
                return client;
        }
        return null;
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
