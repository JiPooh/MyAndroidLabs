package algonquin.cst2335.park0588;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isSpaceChar;
import static java.lang.Character.isUpperCase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Iterator;

import algonquin.cst2335.park0588.databinding.ActivityMainBinding;

/**
 * this is the main class which holds all the functions to check for the password
 * and return a message to the user depending on the chosen password
 * @author Jihoo park
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    /**
     * initiating ActivityMainBinding to enable variable binding
     */
    private ActivityMainBinding variableBinding;
    /**
     * text view which would display the text requesting password in the main page
     */
    TextView text = null;
    /**
     * edit text which would receive password from user
     */
    EditText edit = null;
    /**
     * button for the user to interact with to send in their password for login
     */
    Button butt = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
        //setContentView(R.layout.activity_main);
        text = variableBinding.textView;
        edit = variableBinding.editText;
        butt = variableBinding.butt;

        butt.setOnClickListener( clk -> {
            String pass = edit.getText().toString();
            passChkr(pass);
        });


    }
    /**
     * function to check if the password is complex enough
     * @param pw password in string format that is being checked
     * @return true if password is complex, false if simple
     */
    boolean passChkr(String pw){
        boolean foundUpperCase = false;
        boolean foundLowerCase = false;
        boolean foundNumber = false;
        boolean foundSpecial = false;
        for(int i=0; i<pw.length(); i++){
            char c = pw.charAt(i);
            if(isDigit(c)){
                foundNumber = true;
            }
            if(isUpperCase(c)){
                foundUpperCase = true;
            }
            if(isLowerCase(c)){
                foundLowerCase = true;
            }
            if(isSpecChar(c)){
                foundSpecial = true;
            }
        }
        if(foundNumber == true && foundSpecial == true && foundLowerCase == true && foundUpperCase == true) {
            Toast.makeText(this, "Nice", Toast.LENGTH_SHORT).show();
            text.setText("Nice!");
            return true;
        }else{
            if(!foundNumber){
                Toast.makeText(this, "Missing number in password", Toast.LENGTH_SHORT).show();
            }
            if(!foundSpecial){
                Toast.makeText(this, "Missing special character in password", Toast.LENGTH_SHORT).show();
            }
            if(!foundLowerCase){
                Toast.makeText(this, "Missing lowercase in password", Toast.LENGTH_SHORT).show();
            }
            if(!foundUpperCase){
                Toast.makeText(this, "Missing uppercase in password", Toast.LENGTH_SHORT).show();
            }
            text.setText("You shall not pass!");
            return false;
        }
    }

    /**
     * function to check if char value is a special character specified
     * @param c char value that is being checked if special character
     * @return true if the char value is a special character, false if not
     */
    private boolean isSpecChar(char c) {
        switch (c){
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                return true;
            default: return false;

        }
    }
}