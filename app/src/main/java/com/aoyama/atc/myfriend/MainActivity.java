package com.aoyama.atc.myfriend;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    // Explicit
    private Button signInButton, signUpButton; //ประกาศตัวแปร
    private EditText userEditText, passwordEditText;
    private String userString,passwordString;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //blind widget

        signInButton = (Button) findViewById(R.id.button);
        signUpButton = (Button) findViewById(R.id.button2);
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText5);

        //SignIn Controller
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Value
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //check space
                if ((userString.length()==0)||(passwordString.length()==0)) {
                    MyAlert myAlert = new MyAlert(MainActivity.this,
                            R.drawable.kon48, "ข้อมูลว่าง", "กรุณากรอก Username และ Password");
                    myAlert.myDialog();

                } else {
                    //authen
                    chekUserPassword();
                }


            }//On Click
        });



        //signup control
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }
        });



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }// Main Method

    private void chekUserPassword() {
        String strResult = null, strTruePassword = null;
        boolean bolStatus = true;


        try {

            GetUser getUser = new GetUser(MainActivity.this);
            getUser.execute();
            strResult = getUser.get();
            Log.d("20NovV3", "strResult ==>" + strResult);

            JSONArray jsonArray = new JSONArray(strResult);


            for (int i=0;i<jsonArray.length();i++ ) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (userString.equals(jsonObject.getString("User"))) {
                    bolStatus = false;
                    strTruePassword = jsonObject.getString("Password");
                }



            }//for
            if (bolStatus) {
                MyAlert myAlert = new MyAlert(MainActivity.this,
                        R.drawable.rat48, "User False", "No this User in my Database");
                myAlert.myDialog();

            } else if (passwordString.equals(strTruePassword)) {
                startActivity(new Intent(MainActivity.this, FriendListView.class));


            } else {
                MyAlert myAlert = new MyAlert(MainActivity.this,
                        R.drawable.rat48, "Password False", "Please Typ Again");
                myAlert.myDialog();

            }

        } catch (Exception e) {
            Log.d("20NovV3","e CheckUser==>" + e.toString());
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
} //Main Class

