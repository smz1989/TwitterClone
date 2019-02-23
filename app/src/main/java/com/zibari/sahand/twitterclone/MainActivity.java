package com.zibari.sahand.twitterclone;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // UI components

    private EditText edtEmail,edtUsername,edtPassword;
    private Button btnSignUp,btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Sign Up");

        ParseInstallation.getCurrentInstallation().saveInBackground();

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        // Pressing the button in the popup keyboard /// start

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

                    onClick(btnSignUp);

                }
                return false;
            }
        });

        // Pressing the button in the popup keyboard  /// end

        edtUsername = findViewById(R.id.edtUsername);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLoginActivity);

        btnSignUp.setOnClickListener(MainActivity.this);
        btnLogIn.setOnClickListener(MainActivity.this);

        if (ParseUser.getCurrentUser() != null) {

            transitionTOTwitter();
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnSignUp:

                // Show a message that you should enter all requirements // start

                if (edtEmail.getText().toString().equals("") || edtUsername.getText().toString().equals("") || edtPassword.getText().toString().equals("")){

                    FancyToast.makeText(MainActivity.this, " Email, Username, Password is required! ",
                            Toast.LENGTH_SHORT, FancyToast.INFO, true).show();

                    // Show a message that you should enter all requirements // end



                } else {

                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtEmail.getText().toString());
                    appUser.setUsername(edtUsername.getText().toString());
                    appUser.setPassword(edtPassword.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage(" Signing Up" + edtUsername.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                FancyToast.makeText(MainActivity.this, appUser.getEmail() + " is Signed Up!",
                                        Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();

                                transitionTOTwitter();

                            } else {

                                FancyToast.makeText(MainActivity.this, " There was an Error" + e.getMessage(),
                                        Toast.LENGTH_LONG, FancyToast.ERROR, true).show();



                            }

                            progressDialog.dismiss();

                        }
                    });
                }


                break;


            case R.id.btnLoginActivity:


                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

                break;

        }

    }

    // Keyboard goes away by clicking /// start

    public void rootLayoutTapped(View view){

        try {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        } catch (Exception e){

            e.printStackTrace();
        }

    // Keyboard goes away by clicking /// end
    }

   // Transition to Twitter.Java file // start

    private void transitionTOTwitter(){

        Intent intent = new Intent(MainActivity.this, Twitter.class);
        startActivity(intent);

    }

    // Transition to Twitter.Java file // end

}
