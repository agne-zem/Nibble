package com.programele.nibble.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.programele.nibble.R;
import com.programele.nibble.Recipe;

import java.io.ByteArrayOutputStream;

public class CreateRecipeActivity extends AppCompatActivity implements View.OnClickListener {

    final int GALLERY_REQUEST_CODE = 1;
    Button buttonUpload;
    Button buttonUploadImage;
    Button buttonUploadImage2;
    EditText textViewTitle;
    EditText textviewDescription;
    EditText textIngredients;
    ImageView imageViewRecipe;
    Uri imageUri;
    private ProgressBar mProgressCircle;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);



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


        mProgressCircle = findViewById(R.id.progress_circle);
        mProgressCircle.setVisibility(View.INVISIBLE);


        databaseReference = FirebaseDatabase.getInstance().getReference("recipes");




        //view
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        buttonUploadImage = (Button) findViewById(R.id.buttonPickPhoto);
        buttonUploadImage2 = (Button) findViewById(R.id.uploadPhoto2);

        textViewTitle = (EditText) findViewById(R.id.textViewTitle);
        textviewDescription = (EditText) findViewById(R.id.textDescription);
        textIngredients = (EditText) findViewById(R.id.editTextIngredients);

        imageViewRecipe = (ImageView) findViewById(R.id.imageViewRecipe);
        mProgressCircle = findViewById(R.id.progress_circle);
        buttonUpload.setOnClickListener(this);

        buttonUploadImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressCircle.setVisibility(View.VISIBLE);
                FirebaseUser user = firebaseAuth.getCurrentUser();

                String recipe_id = user.getUid() + System.currentTimeMillis();

                FirebaseStorage storage = FirebaseStorage.getInstance();

                // Create a storage reference from our app
                StorageReference storageRef = storage.getReferenceFromUrl("gs://nibble-30048.appspot.com");

                // Create a reference to "recipe.jpg"
                final StorageReference recipeImageRef = storageRef.child(recipe_id);


                // Get the data from an ImageView as bytes
                imageViewRecipe.setDrawingCacheEnabled(true);
                imageViewRecipe.buildDrawingCache();
                Bitmap bitmap = imageViewRecipe.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = recipeImageRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        recipeImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUrl = uri;
                                imageUri = uri;
                                Toast.makeText(getBaseContext(), "Upload success! URL - " + downloadUrl.toString() , Toast.LENGTH_SHORT).show();
                                mProgressCircle.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                });
            }
        });

        buttonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();

            }
        });
    }


    public void saveRecipe()
    {

        String title = textViewTitle.getText().toString().trim();
        String description = textviewDescription.getText().toString().trim();
        String ingredients_list = textIngredients.getText().toString().trim();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        String recipe_id = user.getUid() + System.currentTimeMillis();



        Recipe recipe = new Recipe(title, ingredients_list, description, user.getUid(), imageUri.toString());
        databaseReference.child(recipe_id).setValue(recipe);
        Toast.makeText(this, "Recipe uploaded", Toast.LENGTH_LONG).show();



    }


    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    private Bitmap bitmap;

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    //data.getData return the content URI for the selected Image
                    imageUri = data.getData();
                    try{
                       bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));




                    }catch (Exception e){
                        e.printStackTrace();
                    }
/*
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    // Set the Image in ImageView after decoding the String
                    */

                    if(bitmap != null) {
                        imageViewRecipe.setImageBitmap(bitmap);

                    }
                    break;

            }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonUpload){
            saveRecipe();

        }
    }
}

