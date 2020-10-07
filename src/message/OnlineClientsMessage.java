package message;

import java.io.Serializable;
import java.util.List;

public class OnlineClientsMessage extends Message implements Serializable {

    List<String> clients;

    public OnlineClientsMessage(List<String> clients) {

        this.clients = clients;
    }

    @Override
    public void handleMessage() {

        System.out.println("Online clients:");
        for (String client: clients) {
            System.out.println(client);
        }
        System.out.println(date);
    }
}
