import java.net.*;
import java.io.*;
import java.util.*;

public class Server{

    final static int PORT = 5000;
    private static List<ClientHandler> clients = new LinkedList<>();

    public static List<ClientHandler> getClients() {
        return clients;
    }

    public static ClientHandler findClient(String name){

        for (ClientHandler client : clients  ) {
            if(client.name.equals(name))
                return client;
        }
        return null;
    }

    public static void main(String[] args) throws IOException{
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
