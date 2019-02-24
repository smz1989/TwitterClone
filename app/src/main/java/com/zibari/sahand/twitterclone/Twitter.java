package com.zibari.sahand.twitterclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class Twitter extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    // For keeping the users inside
    private ArrayList<String> tUsers;

    private ArrayAdapter adapter;
    private String followedUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);





        // Initializing Users in listview  /  ParseQuery  /// Start



        FancyToast.makeText(this, "Welcome" + ParseUser.getCurrentUser().getUsername(), FancyToast.INFO, Toast.LENGTH_LONG, true).show();
        listView = findViewById(R.id.listView);
        tUsers = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, tUsers);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        // For checking or unChecking other users Initialization // Start 1
        listView.setOnItemClickListener(Twitter.this);
        // For checking or unChecking other users // end 1


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

                            // Solving adding users twice when run the apps again /// Start

                        for (String twitterUsers:tUsers){

                            if (ParseUser.getCurrentUser().getList("fanOf") != null) {

                                if (ParseUser.getCurrentUser().getList("fanOf").contains(twitterUsers)) {

                                    followedUser = followedUser + twitterUsers + "\n";

                                    listView.setItemChecked(tUsers.indexOf(twitterUsers), true);

                                    FancyToast.makeText(Twitter.this,ParseUser.getCurrentUser().getUsername() + " is following " + followedUser, Toast.LENGTH_SHORT, FancyToast.INFO, true).show();
                                }
                            }


                        }

                           // Solving adding users twice when run the apps again /// End
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

    // For checking or unChecking other users Initialization // Start 2

    @Override
    public void onItemClick(AdapterView<?> parent,
                            View view, int position, long id) {

        CheckedTextView checkedTextView = (CheckedTextView) view;

        if (checkedTextView.isChecked()) {
            FancyToast.makeText(Twitter.this, tUsers.get(position) + " is now followed", Toast.LENGTH_SHORT, FancyToast.INFO, true).show();

            ParseUser.getCurrentUser().add("fanOf", tUsers.get(position));

        }else{

                              // Unfollowing Users  // Start

            FancyToast.makeText(Twitter.this, tUsers.get(position) + " is now unfollowed", Toast.LENGTH_SHORT, FancyToast.INFO, true).show();

            ParseUser.getCurrentUser().getList("fanOf").remove(tUsers.get(position));
            List currentUsersFanOfList = ParseUser.getCurrentUser().getList("fanOf");
            ParseUser.getCurrentUser().remove("fanOf");
            ParseUser.getCurrentUser().put("fanOf", currentUsersFanOfList);

                            // Unfollowing Users  // End

        }

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null){

                    FancyToast.makeText(Twitter.this, " Saved", Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();

                }

            }
        });


    }

    // For checking or unChecking other users Initialization // end 2
}
