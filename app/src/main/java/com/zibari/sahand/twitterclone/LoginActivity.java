package com.zibari.sahand.twitterclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginEmail, edtLoginPassword;
    private Button btnLoginActivity, btnSignUpActivityLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle(" Log In");


        edtLoginPassword = findViewById(R.id.edtLoginPassword);

        // Pressing the button in the popup keyboard /// start

        edtLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

                    onClick(btnLoginActivity);

                }
                return false;
            }
        });

        // Pressing the button in the popup keyboard  /// end


        edtLoginEmail =findViewById(R.id.edtLoginEmail);

        btnLoginActivity = findViewById(R.id.btnLoginActivity);
        btnSignUpActivityLogin = findViewById(R.id.btnSignUpActivityLogin);

        btnLoginActivity.setOnClickListener(this);
        btnSignUpActivityLogin.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null){

            transitionTOTwitter();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnLoginActivity:

                // Show a message that you should enter all requirements // start

                if (edtLoginEmail.getText().toString().equals("") || edtLoginPassword.getText().toString().equals("")){

                    FancyToast.makeText(LoginActivity.this, " Email, Password is required! ",
                            Toast.LENGTH_SHORT, FancyToast.INFO, true).show();

                 // Show a message that you should enter all requirements // end


                } else {

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage(" Logging in" + edtLoginEmail.getText().toString());
                    progressDialog.show();

                    ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginPassword.getText().toString(),

                            new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {

                                    if (user != null && e == null) {

                                        FancyToast.makeText(LoginActivity.this, user.getUsername() + " is Logged In Successfully!",
                                                Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();

                                        transitionTOTwitter();

                                    }
                                    else {

                                        FancyToast.makeText(LoginActivity.this, " There was an Error" + e.getMessage(),
                                                Toast.LENGTH_LONG, FancyToast.ERROR, true).show();
                                    }

                                    progressDialog.dismiss();
                                }
                            });

                    break;
                }

            case R.id.btnSignUpActivityLogin:

                break;
        }
    }


    // Keyboard goes away by clicking /// start

    public void rootLayoutGoesAway(View view) {

        try {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

        // Keyboard goes away by clicking /// end


    // Transition to Twitter.Java file // start

    private void transitionTOTwitter(){

        Intent intent = new Intent(LoginActivity.this, Twitter.class);
        startActivity(intent);

    }

    // Transition to Twitter.Java file // end


}
