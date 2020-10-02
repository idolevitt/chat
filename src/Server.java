import java.net.*;
import java.io.*;
import java.util.*;

public class Server{

    final static int PORT = 5000;
    static List<ClientHandler> clients = new LinkedList<>();

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
        }
    }

}
