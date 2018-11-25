package com.example.alvin.recipe_book;

import android.net.Uri;


public class ProviderContract {

    // Save the URI info
    final static String AUTHORITY = "com.example.alvin.MyContentProvider";
    final static Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    // Different URI for four kinds of actions
    public interface URI {
        Uri ID_INSERT = Uri.parse("content://" + AUTHORITY + "/recipe/insert");
        Uri ID_QUERY = Uri.parse("content://" + AUTHORITY + "/recipe/query");
        Uri ID_UPDATE = Uri.parse("content://" + AUTHORITY + "/recipe/update");
        Uri ID_DELETE = Uri.parse("content://" + AUTHORITY + "/recipe/delete");
    }
}
