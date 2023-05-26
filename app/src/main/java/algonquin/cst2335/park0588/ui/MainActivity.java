package algonquin.cst2335.park0588.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.park0588.data.MainViewModel;
import algonquin.cst2335.park0588.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding variableBinding;
    private MainViewModel model;

    @Override //start here
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        //variableBinding.textview.setText(model.editString);
        variableBinding.mybutton.setOnClickListener( vw -> {
            model.editString.postValue(variableBinding.myedittext.getText().toString());

        });

        variableBinding.mycheck.setOnCheckedChangeListener((ch, isChecked)->{
            model.buttonQuestion.postValue(variableBinding.mycheck.isChecked());
            Toast.makeText(this, "The value is now: " +
                    variableBinding.mycheck.isChecked(), Toast.LENGTH_SHORT).show();

        });
        variableBinding.myswitch.setOnCheckedChangeListener((sw, isChecked)->{
            model.buttonQuestion.postValue(variableBinding.myswitch.isChecked());
        });
        variableBinding.myradio.setOnCheckedChangeListener((ra, isChecked)->{
            model.buttonQuestion.postValue(variableBinding.myradio.isChecked());
        });

        variableBinding.myimagebutton.setOnClickListener( ic -> {
            Toast.makeText(this,
                    "The width = " + variableBinding.myimagebutton.getWidth() + " " +
                            "and height = " + variableBinding.myimagebutton.getHeight(),
                    Toast.LENGTH_SHORT).show();
        });


        model.editString.observe(this, s -> {
            variableBinding.textview.setText("Your edit text has " + s);
        });

        model.buttonQuestion.observe(this, b -> {
           variableBinding.mycheck.setChecked(b);
           variableBinding.myswitch.setChecked(b);
           variableBinding.myradio.setChecked(b);
        });

    }
}