package com.programele.nibble.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.programele.nibble.R;

public class MenuActivity extends AppCompatActivity {

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void productsrecipes(View view)
    {
        Intent intent = new Intent(this, RecipesActivity.class);
        startActivity(intent);
    }

    public void mykitchen(View view)
    {
        Intent intent = new Intent(this, MyKitchenActivity.class);
        startActivity(intent);
    }

}