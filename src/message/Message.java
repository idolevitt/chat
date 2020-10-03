package message;

import java.util.*;

public abstract class Message {

    Date date;

    public Message(){

        date = new Date();
    }


    public abstract void handleMessage();
}
