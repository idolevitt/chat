package message;

public class PrivateMessage extends Message {

    String sender;
    String text;

    public PrivateMessage(String sender, String text){

        this.sender = sender;
        this.text = text;
    }

    public void handleMessage(){

        System.out.println( sender + ": " + text );
    }
}
