package com.connected.parking.model;

import android.content.SearchRecentSuggestionsProvider;

public class MSearchRecentSuggestionsProvider extends SearchRecentSuggestionsProvider{

    final static String AUTHORITY="com.connected.parking.model.MSearchSuggestionsProvider";   
    final static int MODE=DATABASE_MODE_QUERIES;   
       
    public MSearchRecentSuggestionsProvider(){   
        super();   
        setupSuggestions(AUTHORITY, MODE);   
    }   
}    
