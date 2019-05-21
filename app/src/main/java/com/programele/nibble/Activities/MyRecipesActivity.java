package com.programele.nibble.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programele.nibble.R;
import com.programele.nibble.Recipe;
import com.programele.nibble.RecipesAdapter;

import java.util.ArrayList;

public class MyRecipesActivity extends AppCompatActivity {


    ArrayList<Recipe> mContents;


    ListView mListView;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);
        mListView = findViewById(R.id.recipes_listview);

        databaseReference = FirebaseDatabase.getInstance().getReference("recipes");
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


        mContents = new ArrayList<>();

    }
    @Override
    protected void onStart() {
        super.onStart();

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userid=user.getUid();
        databaseReference.child(userid).setValue(user);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("recipes");
        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren())
                {
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                    mContents.add(recipe);
                }

                RecipesAdapter mAdapter = new RecipesAdapter(mContents, MyRecipesActivity.this);
                mListView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
