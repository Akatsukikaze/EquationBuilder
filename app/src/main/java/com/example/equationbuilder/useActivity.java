package com.example.equationbuilder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.euicc.EuiccInfo;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.text.method.TextKeyListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class useActivity extends AppCompatActivity {
    private Equation equation;
    private LinearLayout eqBackground;
    private SimpleList<EditText> edits;
    private String eq_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use);

        Intent intent = getIntent();
        String title = intent.getStringExtra("describe");
        eq_str = intent.getStringExtra("equation");
        Toolbar mToolbarTb = findViewById(R.id.use_toolbar);
        setSupportActionBar(mToolbarTb);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        equation = new Equation(eq_str);

        eqBackground = findViewById(R.id.eq_bg_use);
        edits = new SimpleList<>();
        addViewByEq();

        Button btn_execute = findViewById(R.id.btn_execute);
        btn_execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vaCount = edits.getCount();
                int i = 0;
                float variates[] = new float[vaCount];
                try {
                    while (i < vaCount) {
                        variates[i] = Float.parseFloat(edits.get(i).getText().toString());
                        i++;
                    }
                }catch (Exception e){
                    new AlertDialog.Builder(useActivity.this)
                            .setTitle("Failed")
                            .setMessage("Executing failed. Please check.")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }
                final String out = Equation.executeEquation(eq_str,vaCount,variates);
                if(out != null){
                    new AlertDialog.Builder(useActivity.this)
                            .setTitle("Answer")
                            .setMessage("The answer is: "+out)
                            .setPositiveButton("OK", null)
                            .show();
                }else{
                    new AlertDialog.Builder(useActivity.this)
                            .setTitle("Failed")
                            .setMessage("Executing failed. Please check.")
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_use, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent myIntent = new Intent(useActivity.this, MainActivity.class);
                startActivity(myIntent);
                useActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent myIntent = new Intent(useActivity.this, MainActivity.class);
            startActivity(myIntent);
            useActivity.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    private void addViewByEq() {
        SimpleList<Boolean> struct = equation.getStruct();
        int count = struct.getCount();
        int i = 0, va = 0, non = 0;
        TextView lastText;
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
    }
    private LinearLayout makeVaView(int va){
        LinearLayout bg = new LinearLayout(useActivity.this);
        bg.setBackgroundColor(Color.rgb(50,50,180));
        LinearLayout.LayoutParams newLinPar =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bg.setLayoutParams(newLinPar);
        bg.setPadding(10,10,10,10);

        TextView newText = new TextView(useActivity.this);
        LinearLayout.LayoutParams newTextPar =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        newText.setLayoutParams(newTextPar);
        String str = ""+va+":";
        newText.setText(str);
        newText.setTextSize(36);
        newText.setBackgroundColor(Color.rgb(50,50,180));
        newText.setTextColor(Color.rgb(255, 255, 255));
        bg.addView(newText);

        final EditText newEdit = new EditText(useActivity.this);
        LinearLayout.LayoutParams newEditPar =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        newEdit.setLayoutParams(newEditPar);
        newEdit.setTextSize(36);
        newEdit.setBackgroundColor(Color.rgb(255, 255, 255));
        newEdit.setTextColor(Color.rgb(0, 0, 0));
        newEdit.setSingleLine();
        newEdit.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        newEdit.setMinWidth(FormUnits.sp2px(this,48));
        newEdit.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.NONE, true) {
            // 0无键盘 1英文键盘 2模拟键盘 3数字键盘
            @Override
            public int getInputType() {
                // TODO Auto-generated method stub
                return 3;
            }
        });
        newEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //这个方法被调用，说明在s字符串中，从start位置开始的count个字符即将被长度为after的新文本所取代。在这个方法里面改变s，会报错。
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //这个方法被调用，说明在s字符串中，从start位置开始的count个字符刚刚取代了长度为before的旧文本。在这个方法里面改变s，会报错。
            }
            @Override
            public void afterTextChanged(Editable s) {
                //这个方法被调用，那么说明s字符串的某个地方已经被改变。
                int i = 0;
                int count = edits.getCount();
                while(i < count){
                    if(edits.get(i) == newEdit){
                        break;
                    }
                    i++;
                }
                int va = equation.getVaByPos(i);
                int j = i + 1;
                while(j < count){
                    if(va == equation.getVaByPos(j)){
                            edits.get(j).setText(s.toString());
                            break;
                        }
                    j++;
                }
            }
        });
        bg.addView(newEdit);
        edits.add(newEdit);


        return bg;
    }
    private TextView makeTextView(String str){
        TextView newText = new TextView(useActivity.this);
        LinearLayout.LayoutParams newTextPar =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        newText.setLayoutParams(newTextPar);
        newText.setText(str);
        newText.setTextSize(48);
        newText.setBackgroundColor(Color.rgb(255, 255, 255));
        newText.setTextColor(Color.rgb(0, 0, 0));
        return newText;
    }
}
