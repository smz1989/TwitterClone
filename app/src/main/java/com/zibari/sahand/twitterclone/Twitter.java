package com.zibari.sahand.twitterclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class Twitter extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> tUsers;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);


        // Initializing Users in listview  /  ParseQuery  /// Start



        FancyToast.makeText(this, "Welcome" + ParseUser.getCurrentUser().getUsername(), FancyToast.INFO, Toast.LENGTH_LONG, true).show();
        listView = findViewById(R.id.listView);
        tUsers = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tUsers);

        try {

            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {

                    if (objects.size() > 0 && e == null){

                        for (ParseUser twitterUser : objects){

                            tUsers.add(twitterUser.getUsername());
                        }

                        listView.setAdapter(adapter);
                    }

                }
            });


        }catch (Exception e){

            e.printStackTrace();

        }


    }

    // Initializing Users in listview  /  ParseQuery  /// END


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


        switch (item.getItemId()){

            case R.id.logoutUserItem:

                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {

                        Intent intent = new Intent(Twitter.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
                break;
        }


        return super.onOptionsItemSelected(item);
    }

}
