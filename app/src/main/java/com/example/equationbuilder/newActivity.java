package com.example.equationbuilder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.*;

public class newActivity extends AppCompatActivity {
    private ArrayList<Integer> variate;
    private String equationStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Toolbar mToolbarTb = (Toolbar) findViewById(R.id.ne_toolbar);
        setSupportActionBar(mToolbarTb);

        variate = new ArrayList<Integer>();
        equationStr = "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_nv:

                break;
            case R.id.menu_item_dv:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
