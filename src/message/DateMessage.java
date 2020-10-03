package message;

public class DateMessage extends Message {
    @Override
    public void handleMessage() {
        System.out.println(this.date);
    }
}
