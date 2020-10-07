package message;
import java.io.Serializable;

public class DateMessage extends Message implements Serializable {
    @Override
    public void handleMessage() {
        System.out.println(this.date);
    }
}
