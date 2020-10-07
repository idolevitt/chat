package message;

import java.io.Serializable;

public class CustomMessage extends Message implements Serializable {

    String text;

    public CustomMessage(String text){
        this.text = text;
    }

    public void handleMessage() {
        System.out.println(this.text);
    }
}

