package algonquin.cst2335.park0588;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import algonquin.cst2335.park0588.databinding.ActivityMainBinding;
import algonquin.cst2335.park0588.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {
    private ActivitySecondBinding variableBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        variableBinding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
        //setContentView(R.layout.activity_second);
        Intent fromPrev = getIntent();
        String emailAddress = fromPrev.getStringExtra("EmailAddress");
        variableBinding.textView3.setText("Welcome back, " + emailAddress);

        variableBinding.callButt.setOnClickListener(clk->{
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + variableBinding.editTextPhone.getText().toString()));
            startActivity(call);
        });

        ActivityResultLauncher <Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override

                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            variableBinding.imageView.setImageBitmap( thumbnail );

                        }

                    }

                });

        variableBinding.imgButt.setOnClickListener(clc->{
            Intent camInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


            cameraResult.launch(camInt);
        });
    }
}