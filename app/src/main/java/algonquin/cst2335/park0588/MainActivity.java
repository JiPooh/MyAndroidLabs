package algonquin.cst2335.park0588;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import algonquin.cst2335.park0588.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding variableBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
        Log.w("MainActivity","In onCreate() - Loading Widgets");

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("LoginName", "");

        variableBinding.logButt.setOnClickListener( clk-> {
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            nextPage.putExtra("EmailAddress", variableBinding.email.getText().toString());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LoginName", variableBinding.email.getText().toString());
            editor.apply();
            startActivity(nextPage);
        });
        /*Button nextButt = findViewById(R.id.logButt);
        nextButt.setOnClickListener( clk-> {
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(nextPage);
        });*/
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.w("MainActivity", "In onStart() - The Application is now visible on screen.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "In onResume() - The application is now responding to user input.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "In onPause() - The application no longer responds to user input.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w("MainActivity", "In onStop() - The application is no longer visible.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("MainActivity", "In onDestroy() - Any memory used by the application is freed.");
    }

}