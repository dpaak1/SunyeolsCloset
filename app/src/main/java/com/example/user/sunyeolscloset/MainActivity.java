package com.example.user.sunyeolscloset;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CustomView customView;
  //  Button bodybutton;
     Button penbutton;
    ImageButton randombutton;
    ImageButton shopbutton;
    ImageView body;
    static ImageView redbar;
    static TextView talkview;

    public static CustomDialog cumtomdialog;
    public MyAdapter mAdapter;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       activity = this;

        customView = (CustomView) findViewById(R.id.custom_view);
        penbutton = (Button) findViewById(R.id.pen_button);
        penbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                customView.setItemNum(3);
            }
        });
        randombutton = (ImageButton) findViewById(R.id.random_button);
        randombutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Math.random() < 0.3) {
                    customView.setItemNum(5);
                    customView.randomlist[0] = true;
                    Toast.makeText(getApplicationContext(), "Get Item", Toast.LENGTH_SHORT).show();
                } else if (Math.random() > 0.8) {
                    // customView.setItemNum(5);
                } else {
                    Toast.makeText(getApplicationContext(), "빈박스 사기당했다! -3000", Toast.LENGTH_SHORT).show();
                    //talkview.setText("건드리지마ㅡㅡ");
                }
            }
        });
        redbar = (ImageView) findViewById(R.id.redbar);
        talkview = (TextView) findViewById(R.id.talkview);

        mAdapter = new MyAdapter(activity);

        shopbutton = (ImageButton) findViewById(R.id.shop_button);
        shopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Custom Dialog
                cumtomdialog = new CustomDialog(activity, mAdapter);
                cumtomdialog.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()){
            case R.id.blackpen :
                customView.penBlack();
                item.setChecked(true);
                return true;
            case R.id.redpen :
                customView.penRed();
                item.setChecked(true);
                return true;
            case R.id.yellowpen :
                customView.penyellow();
                item.setChecked(true);
                return true;
            case R.id.greenpen :
                customView.penGreen();
                item.setChecked(true);
                return true;
            case R.id.bluepen :
                customView.penBlue();
                item.setChecked(true);
                return true;
            case R.id.eraser :
                item.setChecked(true);
                customView.eraser();
                return true;
            case R.id.remover :
                customView.remover();
                return true;
        }
        return  super.onOptionsItemSelected(item);
    }

    public void startAnimation(){
        body = (ImageView) findViewById(R.id.body_view);
        AnimationDrawable anim = (AnimationDrawable)body.getDrawable();
        anim.start();
    }

    public  void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            startAnimation();
        }
    }
    public static void stressUp() {
        Log.d("myapps","he");
        ViewGroup.LayoutParams param = redbar.getLayoutParams();
        param.height += 1;
        redbar.setLayoutParams(param);
    }
    public static void stressDown(int num){
        ViewGroup.LayoutParams param = redbar.getLayoutParams();
        param.height -= num;
        redbar.setLayoutParams(param);
    }



}
