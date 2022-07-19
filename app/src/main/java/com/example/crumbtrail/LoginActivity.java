package com.example.crumbtrail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.parse.CountCallback;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private ProgressDialog progressDialog;
    public static final int RC_SIGN_IN = 7;
    GoogleSignInClient mGoogleSignInClient;
    public static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(LoginActivity.this);

        if (ParseUser.getCurrentUser() != null) goMainActivity();
        setUpGoogleSignIn();

        username = findViewById(R.id.usernameEt);
        password = findViewById(R.id.passwordEt);
        Button login = findViewById(R.id.loginBtn);
        Button navigateSignup = findViewById(R.id.navigatesignup);
        SignInButton btnGoogleSignIn = findViewById(R.id.sign_in_button);


        login.setOnClickListener(v -> login(Objects.requireNonNull(username.getText()).toString(), Objects.requireNonNull(password.getText()).toString()));
        Objects.requireNonNull(btnGoogleSignIn).setOnClickListener(v -> {
            if (v.getId() == R.id.sign_in_button) {
                signIn();
            }
        });

        navigateSignup.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));

    }

    private void login(String username, String password) {
        progressDialog.show();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        // TODO: Add checks to prevent breaking when user clicks sign in accidentally
        if (account != null) {
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("username", account.getEmail());

            query.countInBackground((count, e) -> {
                if (e == null) {
                    if(count==0){
                        if (account.getDisplayName() == null) Log.i(TAG, "Display name is null");
                        if (account.getIdToken() == null) Log.i(TAG, "Token is null");
                        ParseUser.logInInBackground(Objects.requireNonNull(account.getEmail()), account.getIdToken(), (parseUser, parseException) -> {
                            progressDialog.dismiss();
                            if (parseUser != null) {
                                Toasty.success(LoginActivity.this, "Successful login", Toast.LENGTH_LONG).show();
                            } else {
                                ParseUser.logOut();
                                Toasty.error(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            });
        }
        ParseUser.logInInBackground(username, password, (parseUser, e) -> {
            progressDialog.dismiss();
            if (parseUser != null) {
                Toasty.success(LoginActivity.this, "Successful login", Toast.LENGTH_LONG).show();
            } else {
                ParseUser.logOut();
                Toasty.error(LoginActivity.this, Objects.requireNonNull(e.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null && ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.d(TAG, "Google sign in works");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "OnActivityResult started");
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            Log.d(TAG, "OnActivityResult returned");
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
//            login(account.getDisplayName(), account.getId());
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }



    private void goMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

}