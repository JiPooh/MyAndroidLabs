package algonquin.cst2335.park0588;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;
    @ColumnInfo(name="message")
    protected String message;
    @ColumnInfo(name="timeSent")
    protected String timeSent;
    @ColumnInfo(name="isSentButton")
    protected boolean isSentButton;
    ChatMessage(){}
    ChatMessage(String m, String t, boolean sent){
        message = m;
        timeSent = t;
        isSentButton = sent;
    }


    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }
    public boolean getIsSentButton(){
        return isSentButton;
    }
}
