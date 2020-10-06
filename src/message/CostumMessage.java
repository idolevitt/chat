package message;

public class CostumMessage extends Message{

    String text;

    public CostumMessage(String text){
        this.text = text;
    }

    public void handleMessage() {
        System.out.println(this.text);
    }
}

