package com.programele.nibble.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.programele.nibble.R;

public class MyKitchenActivity extends AppCompatActivity implements View.OnClickListener {

    //view objects
    private Button MyRecipesButton;
    private Button MyInfoButton;
    private Button LogOutButton;
    private Button CreateRecipeButton;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_kitchen);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views


        LogOutButton = (Button) findViewById(R.id.log_out);
        MyRecipesButton = (Button) findViewById((R.id.myRecipesButton));
        MyInfoButton = (Button) findViewById((R.id.infoButton));
        CreateRecipeButton = (Button) findViewById((R.id.createRecipeButton));



        //adding listener to button
        LogOutButton.setOnClickListener(this);
        MyRecipesButton.setOnClickListener(this);
        MyInfoButton.setOnClickListener(this);

        CreateRecipeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == LogOutButton)
        {
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        if(v == MyInfoButton)
        {
            startActivity(new Intent(getApplicationContext(), MyInfoActivity.class));


        }
        if(v == MyRecipesButton)
        {
            startActivity(new Intent(getApplicationContext(), MyRecipesActivity.class));


        }
        if(v == CreateRecipeButton)
        {
            startActivity(new Intent(getApplicationContext(), CreateRecipeActivity.class));


        }





    }


}
