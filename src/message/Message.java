package message;

import java.io.Serializable;
import java.util.*;

public abstract class Message implements Serializable {

    Date date;

    public Message(){

        date = new Date();
    }


    public abstract void handleMessage();
}
