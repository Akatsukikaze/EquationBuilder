package com.example.equationbuilder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences equationSP;
    private Map<String,?> equationMap;
    private String[] equationNames;
    private String[] equations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbarTb = (Toolbar) findViewById(R.id.tb_toolbar);
        setSupportActionBar(mToolbarTb);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        equationSP = getSharedPreferences("equations",MODE_PRIVATE);

        /*
        SharedPreferences.Editor editor = equationSP.edit();
        editor.putString("test1","value1");
        editor.putString("test2","value2");
        editor.commit();
        */

        equationMap = equationSP.getAll();
        int mapSize = equationMap.size();
        equationNames = new String[mapSize];
        equations = new String[mapSize];
        int i = 0;
        for (Map.Entry<String, ?> entry : equationMap.entrySet()) {
            equationNames[i] = entry.getKey();
            equations[i] = entry.getValue().toString();
            i++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this, R.layout.item_layout, R.id.item_text, equationNames);
        ListView listView = (ListView) findViewById(R.id.list_view_main);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(itemClickListener);
        listView.setOnItemLongClickListener(itemLongClickListener);
    }
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String describe = equationNames[position];
            String equation = equations[position];

            Intent intent = new Intent(MainActivity.this, useActivity.class);
            intent.putExtra("describe",describe);
            intent.putExtra("equation",equation);
            startActivity(intent);
            MainActivity.this.finish();
        }
    };
    private AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete")
                    .setMessage("Confirm deleting equation \'"+equationNames[position]+"\'?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = equationSP.edit();
                            editor.remove(equationNames[position]);
                            editor.commit();

                            equationMap = equationSP.getAll();
                            int mapSize = equationMap.size();
                            equationNames = new String[mapSize];
                            int i = 0;
                            for (Map.Entry<String, ?> entry : equationMap.entrySet()) {
                                equationNames[i] = entry.getKey();
                                i++;
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                    MainActivity.this, R.layout.item_layout, R.id.item_text, equationNames);
                            ListView listView = (ListView) findViewById(R.id.list_view_main);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(itemClickListener);
                            listView.setOnItemLongClickListener(itemLongClickListener);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*
            case android.R.id.home:
                Log.d("123","123");
                break;
                */
            case R.id.menu_item_new:
                Intent intent = new Intent(MainActivity.this, newActivity.class);
                startActivity(intent);
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
