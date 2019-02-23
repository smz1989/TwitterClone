package com.zibari.sahand.twitterclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;

public class Twitter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);
    }

    // Transition to Twitter.Java file // end

    // creating menu on top of the folder //start  (Adding Menu- Sharing Images)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);


        return super.onCreateOptionsMenu(menu);

        // creating menu on top of the folder //End  (Adding Menu- Sharing Images)
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logoutUserItem){

            ParseUser.getCurrentUser().logOut();
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

}
