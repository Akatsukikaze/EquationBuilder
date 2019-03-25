package com.example.equationbuilder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class newActivity extends AppCompatActivity {
    private Equation equation;
    private SimpleList<Button> vaButtons;
    private LinearLayout vaBackground;
    private LinearLayout eqBackground;
    private TextView lastText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Toolbar mToolbarTb = findViewById(R.id.ne_toolbar);
        setSupportActionBar(mToolbarTb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vaBackground = findViewById(R.id.va_bg_new);
        eqBackground = findViewById(R.id.eq_bg_new);

        equation = new Equation();
        vaButtons = new SimpleList<>();

        addButtonLsn();
    }
    private void addButtonLsn(){
        Button btn = findViewById(R.id.button_0);
        btn.setOnClickListener(lsn_normalBtn);
        btn = findViewById(R.id.button_1);
        btn.setOnClickListener(lsn_normalBtn);
        btn = findViewById(R.id.button_2);
        btn.setOnClickListener(lsn_normalBtn);
        btn = findViewById(R.id.button_3);
        btn.setOnClickListener(lsn_normalBtn);
        btn = findViewById(R.id.button_4);
        btn.setOnClickListener(lsn_normalBtn);
        btn = findViewById(R.id.button_5);
        btn.setOnClickListener(lsn_normalBtn);
        btn = findViewById(R.id.button_6);
        btn.setOnClickListener(lsn_normalBtn);
        btn = findViewById(R.id.button_7);
        btn.setOnClickListener(lsn_normalBtn);
        btn = findViewById(R.id.button_8);
        btn.setOnClickListener(lsn_normalBtn);
        btn = findViewById(R.id.button_9);
        btn.setOnClickListener(lsn_normalBtn);
        btn = findViewById(R.id.button_add);
        btn.setOnClickListener(lsn_normalBtn);
        btn = findViewById(R.id.button_sub);
        btn.setOnClickListener(lsn_normalBtn);
        btn = findViewById(R.id.button_mul);
        btn.setOnClickListener(lsn_normalBtn);
        btn = findViewById(R.id.button_div);
        btn.setOnClickListener(lsn_normalBtn);
        btn = findViewById(R.id.button_dot);
        btn.setOnClickListener(lsn_normalBtn);
        btn = findViewById(R.id.button_bracketL);
        btn.setOnClickListener(lsn_normalBtn);
        btn = findViewById(R.id.button_bracketR);
        btn.setOnClickListener(lsn_normalBtn);
        btn = findViewById(R.id.button_back);
        btn.setOnClickListener(lsn_backspace);
        btn = findViewById(R.id.button_clean);
        btn.setOnClickListener(lsn_clean);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                new AlertDialog.Builder(newActivity.this)
                        .setTitle("Delete")
                        .setMessage("Confirm leaving without save?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent myIntent = new Intent(newActivity.this, MainActivity.class);
                                startActivity(myIntent);
                                newActivity.this.finish();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                break;
            case R.id.menu_item_nv:{
                int vaCount = equation.newVa();
                Button newVa = makeVaButton(vaCount);

                vaButtons.add(newVa);
                vaBackground.addView(newVa);
            }
                break;
            case R.id.menu_item_save:{
                final String out = equation.makeEquation();
                if(out == null){
                    new AlertDialog.Builder(newActivity.this)
                            .setTitle("Mistake")
                            .setMessage("Failed to build equation. Please check your equation.")
                            .setPositiveButton("OK", null)
                            .show();
                }else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(newActivity.this);
                    builder.setTitle("Save");
                    final EditText editText = new EditText(newActivity.this);
                    builder.setView(editText);
                    builder.setMessage("Describe your equation. Please make it different with others.");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            final SharedPreferences sp = getSharedPreferences("equations",MODE_PRIVATE);
                            final SharedPreferences.Editor editor = sp.edit();
                            String a = editText.getText().toString();
                            editor.putString(a,out);
                            editor.commit();
                            Intent myIntent = new Intent(newActivity.this, MainActivity.class);
                            startActivity(myIntent);
                            newActivity.this.finish();
                        }
                    });
                    builder.setNegativeButton("Cancel", null);
                    builder.show();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(newActivity.this)
                    .setTitle("Delete")
                    .setMessage("Confirm leaving without save?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myIntent = new Intent(newActivity.this, MainActivity.class);
                            startActivity(myIntent);
                            newActivity.this.finish();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
        return super.onKeyDown(keyCode, event);
    }
    private View.OnClickListener lsn_va = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tarVa = getOnClickVaPos(v) + 1;
            int res = equation.addVa(tarVa);
            if(res == 0){
                LinearLayout newView = makeVaView(tarVa);
                eqBackground.addView(newView);
                lastText = null;
            }
        }
    };
    private View.OnLongClickListener llsn_va = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            final int pos = getOnClickVaPos(v);
            if(pos!=-1){
                final int res = equation.removeVa(pos+1);
                if(res == 0){
                    new AlertDialog.Builder(newActivity.this)
                            .setTitle("Delete")
                            .setMessage("Confirm deleting variate"+(pos+1)+"?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    vaBackground.removeAllViews();
                                    vaButtons.clean();
                                    int count = equation.getVaCount();
                                    int i = 0;
                                    while(i<count){
                                        Button newBtn = makeVaButton(i+1);
                                        vaButtons.add(newBtn);
                                        vaBackground.addView(newBtn);
                                        i++;
                                    }
                                }
                            })
                        .setNegativeButton("Cancel", null)
                        .show();
                }else{
                    new AlertDialog.Builder(newActivity.this)
                            .setTitle("Hint")
                            .setMessage("Variate"+(pos+1)+" is being used. Please remove all usage of variate"+(pos+1)+" before deleting it.")
                            .setPositiveButton("OK", null)
                            .show();
                }


            }
            return true;
        }
    };
    private View.OnClickListener lsn_normalBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button)v;
            char c = button.getText().charAt(0);
            switch(c){
                case 'x':
                    c = '*';
                    break;
                case '÷':
                    c = '/';
                    break;
                default:
            }
            boolean res = equation.addChar(c);
            if(res){
                if(lastText == null){
                    lastText = makeTextView(""+c);
                    eqBackground.addView(lastText);
                }else{
                    String str = lastText.getText().toString();
                    str+=c;
                    lastText.setText(str);
                }
            }
        }
    };
    private View.OnClickListener lsn_backspace = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            equation.backspace();
            addViewByEq();
        }
    };
    private View.OnClickListener lsn_clean = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            equation.cleanAll();
            eqBackground.removeAllViews();
            lastText = null;
        }
    };
    private Button makeVaButton(int vaCount){
        Button newVa = new Button(newActivity.this);
        newVa.setMinHeight(0);
        newVa.setMinWidth(0);
        newVa.setMinimumHeight(0);
        newVa.setMinimumWidth(0);
        LinearLayout.LayoutParams newBtnPar =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        newBtnPar.setMargins(15,25,15,25);
        newVa.setLayoutParams(newBtnPar);
        newVa.setPadding(25,10,25,10);
        newVa.setTypeface(Typeface.DEFAULT_BOLD);
        newVa.setTextColor(Color.rgb(30,30,120));
        newVa.setBackgroundColor(Color.rgb(255, 255, 255));
        newVa.setTextSize(16);
        String str = "variate"+vaCount;
        newVa.setText(str);
        newVa.setOnClickListener(lsn_va);
        newVa.setOnLongClickListener(llsn_va);

        return newVa;
    }
    private void addViewByEq(){
        eqBackground.removeAllViews();
        SimpleList<Boolean> struct = equation.getStruct();
        int count = struct.getCount();
        if(count>0) {
            int i = 0, va = 0, non = 0;
            while (i < count) {
                if (struct.get(i)) {
                    eqBackground.addView(makeVaView(equation.getVaByPos(va)));
                    va++;
                } else {
                    lastText = makeTextView(equation.getNonByPos(non));
                    eqBackground.addView(lastText);
                    non++;
                }
                i++;
            }
            if (struct.get(count - 1)) {
                lastText = null;
            }
        }else{
            lastText = null;
        }
    }
    private LinearLayout makeVaView(int va){
        LinearLayout bg = new LinearLayout(newActivity.this);
        bg.setBackgroundColor(Color.rgb(50,50,180));
        LinearLayout.LayoutParams newLinPar =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        newLinPar.gravity = Gravity.CENTER;
        bg.setLayoutParams(newLinPar);
        bg.setGravity(Gravity.CENTER_VERTICAL);
        TextView newText = new TextView(newActivity.this);
        LinearLayout.LayoutParams newTextPar =
                new LinearLayout.LayoutParams(FormUnits.sp2px(this,48),FormUnits.sp2px(this,48));
        newTextPar.setMargins(10,10,10,10);
        newText.setLayoutParams(newTextPar);
        newText.setGravity(Gravity.CENTER);
        String str = ""+va;
        newText.setText(str);
        newText.setTextSize(36);
        newText.setBackgroundColor(Color.rgb(255, 255, 255));
        newText.setTextColor(Color.rgb(0, 0, 0));

        bg.addView(newText);

        return bg;
    }
    private TextView makeTextView(String str){
        TextView newText = new TextView(newActivity.this);
        LinearLayout.LayoutParams newTextPar =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        newText.setLayoutParams(newTextPar);
        newTextPar.gravity = Gravity.CENTER;
        newText.setText(str);
        newText.setTextSize(48);
        newText.setBackgroundColor(Color.rgb(255, 255, 255));
        newText.setTextColor(Color.rgb(0, 0, 0));
        return newText;
    }
    private int getOnClickVaPos(View v){
        int i = 0;
        View temp = vaButtons.get(i);
        while(temp != v && temp != null){
            i++;
            temp = vaButtons.get(i);
        }
        if(temp == null) {
            return -1;
        }else{
            return i;
        }
    }

}
