package message;

public class HelpMessage extends Message{

    @Override
    public void handleMessage() {
        System.out.println(
                " ______________________________________"+
                "|________________Manual________________|\n" +
                "|private message:   <message>@recipient|\n" +
                "|broadcast message: <message>@all      |\n" +
                "|who's online?:     /online\n          |\n" +
                "|whats the time?:   /time\n            |\n" +
                "|______________________________________|");
    }
}
