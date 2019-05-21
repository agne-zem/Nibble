package com.programele.nibble.Activities;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programele.nibble.R;
import com.programele.nibble.Recipe;
import com.programele.nibble.RecipesAdapter;

import java.util.ArrayList;


public class RecipesActivity extends AppCompatActivity {

    ArrayList<Recipe> mContents;


    ListView mListView;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        mListView = findViewById(R.id.recipes_listview);

        databaseReference = FirebaseDatabase.getInstance().getReference("recipes");

        mContents = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren())
                {
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                    mContents.add(recipe);
                }

                RecipesAdapter mAdapter = new RecipesAdapter(mContents, RecipesActivity.this);
                mListView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
