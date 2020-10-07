package message;

import java.io.Serializable;

public class HelpMessage extends Message implements Serializable {

    @Override
    public void handleMessage() {
        System.out.println(
                " ______________________________________\n"+
                "|________________Manual________________|\n" +
                "|private message:   <message>@recipient|\n" +
                "|broadcast message: <message>@all      |\n" +
                "|who's online?:     /online            |\n" +
                "|whats the time?:   /time              |\n" +
                "|______________________________________|");
    }
}
