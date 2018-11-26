package com.example.alvin.recipe_book;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/*
    All recipes stored in the database shown in listview
 */
public class ViewActivity extends AppCompatActivity {

    private Context mContext;
    private SQLiteDatabase db;
    private MyDBOpenHelper myDBHelper;

    private ListView lv;
    private Cursor cursor;
    private Intent intent;
    private Bundle bundle;

    private int i = 0;
    private int[] allTitlesId;
    private int selectedId;
    private String[] allRecipesTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        mContext = ViewActivity.this;
        myDBHelper = new MyDBOpenHelper(mContext, "my.db", null, 1);
        db = myDBHelper.getWritableDatabase();

        setListViewAdapter();
    }

    private void setListViewAdapter() {

        i = 0;
        allTitlesId = new int[getCount()];
        allRecipesTitles = new String[getCount()];
        getAllRecipes();

        bundle = new Bundle();
        lv = (ListView) findViewById(R.id.RecipesListView);
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.custom_textview, allRecipesTitles));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {

                selectedId = allTitlesId[myItemInt];
                intent = new Intent(ViewActivity.this, RecipeDetailsActivity.class);
                // Get id of the selected recipe record
                bundle.putInt("id", selectedId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    // Get the total number of recipe record in the table
    public int getCount()
    {
        Cursor cursor =  db.rawQuery("SELECT COUNT (*) FROM recipe",null);
        cursor.moveToFirst();
        int result = cursor.getInt(0);
        cursor.close();
        return result;
    }

    // Get title for all recipes' record from the database table
    public void getAllRecipes() {

        cursor = db.query("recipe", null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                allRecipesTitles[i] = cursor.getString(cursor.getColumnIndex("title"));
                allTitlesId[i] = cursor.getInt(cursor.getColumnIndex("recipeId"));
                i++;
            } while(cursor.moveToNext());
        }
    }

    // Go back to main activity when button is clicked
    public void backToMain(View v) {
        finish();
    }

    // Update the listView after updating or deleting a recipe
    @Override
    public void onResume() {
        super.onResume();
        setListViewAdapter();
    }
}
