package com.example.crumbtrail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crumbtrail.databinding.ActivityComposeBinding;


public class ComposeActivity extends AppCompatActivity {

    public static final String TAG = "ComposeActivity";
    public static final int MAX_REVIEW_LENGTH = 200;

    EditText etComposeScr;
    Button btnTweet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityComposeBinding binding = ActivityComposeBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        btnTweet = binding.btnReview;
        etComposeScr = binding.etComposeScr;

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviewContent = etComposeScr.getText().toString();
                if (reviewContent.isEmpty()) {
                    Toast.makeText(ComposeActivity.this, "Sorry, your review cannot be empty.",Toast.LENGTH_LONG).show();
                    return;
                }
                if (reviewContent.length() > MAX_REVIEW_LENGTH) {
                    Toast.makeText(ComposeActivity.this, "Sorry, your review is too long.",Toast.LENGTH_LONG).show();
                    return;
                }

                // TODO: Logic for saving a new review to parse, finish and send back to previous view, and update it!
            }
        });
    }
}