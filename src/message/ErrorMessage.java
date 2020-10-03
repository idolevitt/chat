package message;

public class ErrorMessage extends Message {

    @Override
    public void handleMessage() {
        System.out.println("ERROR-invalid message \n" +
                "type /help for help");
    }
}
