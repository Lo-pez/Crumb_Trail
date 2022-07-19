package com.example.crumbtrail;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.textfield.TextInputEditText;
import com.parse.ParseUser;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText username;
    private TextInputEditText password;
    private TextInputEditText passwordagain;
    private ProgressDialog progressDialog;
    private GoogleSignInAccount googleSignInAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.username);
        ImageView back = findViewById(R.id.back);
        Button signUp = findViewById(R.id.signup);
        password = findViewById(R.id.password);
        passwordagain = findViewById(R.id.passwordagain);

        Intent intent = getIntent();
        if (intent != null) {
            googleSignInAccount = intent.getParcelableExtra("account");
            if (googleSignInAccount != null)
                username.setText(googleSignInAccount.getDisplayName());
        }

        progressDialog = new ProgressDialog(SignUpActivity.this);

        signUp.setOnClickListener(v -> {
            if (Objects.requireNonNull(password.getText()).toString().equals(Objects.requireNonNull(passwordagain.getText()).toString()) && !TextUtils.isEmpty(Objects.requireNonNull(username.getText()).toString()))
                signUp(username.getText().toString(), password.getText().toString(), googleSignInAccount);
            else
                Toasty.warning(this, "Make sure that the values you entered are correct.", Toast.LENGTH_SHORT).show();
        });

        back.setOnClickListener(v -> finish());

    }

    private void signUp(String username, String password, GoogleSignInAccount account) {
        progressDialog.show();
        ParseUser user = new ParseUser();
        // Set the user's username and password, which can be obtained by a forms
        if (account != null) {
            user.setUsername(account.getDisplayName());
            user.setPassword(account.getIdToken());
        }
        else {
            user.setUsername(username);
            user.setPassword(password);
        }
        user.signUpInBackground(e -> {
            progressDialog.dismiss();
            if (e == null) {
                showAlert("Welcome " + username + " !");
            } else {
                ParseUser.logOut();
                Toasty.error(SignUpActivity.this, Objects.requireNonNull(e.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
    }

    // TODO: Find a way to abstract showAlert
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this)
                .setTitle("Successful Sign Up ! You logged in...\n")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    // don't forget to change the line below with the names of your Activities
                    Intent intent = new Intent(SignUpActivity.this, LogoutActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    private void goMainActivity() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

}