package message;

import java.io.Serializable;

public class PrivateMessage extends Message implements Serializable {

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
