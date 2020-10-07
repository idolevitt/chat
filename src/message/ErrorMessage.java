package message;

import java.io.Serializable;

public class ErrorMessage extends Message implements Serializable {

    @Override
    public void handleMessage() {
        System.out.println("ERROR-invalid message \n" +
                "type /help for help");
    }
}
