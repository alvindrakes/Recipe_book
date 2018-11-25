package com.example.alvin.recipe_book;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
    This a a recipe book app.
    It has 3 activities:
    MainActivity which is the homepage,
    ViewActivity that show the list of recipes stored,
    RecipeDetailActivity that let you change or delete your stored recipes

    MyDBOpenHelper is the database connected to ProvideContract and RecipeContentProvider
 */

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    private Button btn_showAll;

    private EditText titleText;
    private EditText contentText;

    private SQLiteDatabase db;
    private MyDBOpenHelper myDBHelper;
    private ContentResolver resolver;

    String title;
    String contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        myDBHelper = new MyDBOpenHelper(mContext, "my.db", null, 1);

        resolver = this.getContentResolver();
        db = myDBHelper.getWritableDatabase();

        btn_showAll = findViewById(R.id.btn_showAll);

        // show all recipes list when button is clicked
        btn_showAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(viewIntent);
            }
        });
    }

    public void addBtnOnClick(View v) {

        titleText = (EditText) findViewById(R.id.EditableTitle);
        contentText = (EditText) findViewById(R.id.ContentArea);

        title = titleText.getText().toString();
        contents = contentText.getText().toString();

        // Don't add the recipe value if the fields are not fully filled up
        if (title.equals("") || contents.equals("") ) {
            Toast.makeText(getApplicationContext(), "Make sure all fields are filled up", Toast.LENGTH_SHORT).show();

        } else {

            // add newly inserted recipe into ContentValue
            ContentValues insertValue = new ContentValues();
            insertValue.put("title", title);
            insertValue.put("contents", contents);

            resolver.insert(ProviderContract.URI.ID_INSERT, insertValue);
            Toast.makeText(getApplicationContext(), "New recipe added", Toast.LENGTH_SHORT).show();

            // clear EditText content when recipe is successfully added
            titleText.setText("");
            contentText.setText("");
        }
    }
}
