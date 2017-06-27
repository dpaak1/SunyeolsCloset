package com.example.user.sunyeolscloset;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.user.sunyeolscloset.CustomView.getClothesData;

public class MainActivity extends AppCompatActivity {

    CustomView customView;
    //  Button bodybutton;
    ImageButton randombutton;
    ImageButton shopbutton;
    ImageButton penbutton;
    ImageView body;
    static ImageView redbar;
    static TextView talkview;
    static TextView moneyview;
    boolean penonoff = false;

    public static CustomDialog cumtomdialog;
    static CustomSmallDialog customSmallDialog;
    public MyAdapter mAdapter;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        customSmallDialog = new CustomSmallDialog(this);
        customView = (CustomView) findViewById(R.id.custom_view);
        penbutton = (ImageButton) findViewById(R.id.pen_button);
        penbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!penonoff) {
                    penbutton.setImageResource(R.drawable.penb);
                    Toast.makeText(getApplicationContext(), "길게 누르면 색상 변경이 가능합니다", Toast.LENGTH_SHORT).show();
                    customView.setItemNum(9);
                    penonoff = true;
                } else {
                    penbutton.setImageResource(R.drawable.penbx);
                    customView.setItemNum(0);
                    penonoff = false;
                }
            }
        });
        registerForContextMenu(penbutton);
        penbutton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });

        randombutton = (ImageButton) findViewById(R.id.random_button);
        randombutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Math.random() < 0.1 && !getClothesData(41).itemVisible) {
                    customView.setItemTrue(41);
                    customView.invalidate();
                    Toast.makeText(getApplicationContext(), "한복을 얻었다!", Toast.LENGTH_SHORT).show();
                    Log.d("myapp", String.valueOf(moneyview.getText()));
                    moneyview.setText(String.valueOf(Integer.parseInt((String) moneyview.getText()) - 3000));
                } else if (Math.random() > 0.8 && !getClothesData(42).itemVisible) {
                    customView.setItemTrue(42);
                    customView.invalidate();
                    Toast.makeText(getApplicationContext(), "옛날 교복을 얻었다!", Toast.LENGTH_SHORT).show();
                    Log.d("myapp", String.valueOf(moneyview.getText()));
                    moneyview.setText(String.valueOf(Integer.parseInt((String) moneyview.getText()) - 3000));
                } else {
                    Toast.makeText(getApplicationContext(), "빈박스 사기당했다! -3000", Toast.LENGTH_SHORT).show();
                    Log.d("myapp", String.valueOf(moneyview.getText()));
                    moneyview.setText(String.valueOf(Integer.parseInt((String) moneyview.getText()) - 3000));
                    talkview.setText("멍청하기는");
                }
            }
        });
        //-------stress
        redbar = (ImageView) findViewById(R.id.redbar);
        talkview = (TextView) findViewById(R.id.talkview);
        moneyview = (TextView) findViewById(R.id.moneyview);
        //--------shop
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

    public static void stressUp(int i) {

        ViewGroup.LayoutParams param = redbar.getLayoutParams();
        param.height += i;
        if (param.height>1200){
            customSmallDialog.show();
            talkview.setText("꺼져");
        }
        Log.d("myapp",""+ param.height);
        redbar.setLayoutParams(param);
    }

    public static void stressDown(int num) {
        ViewGroup.LayoutParams param = redbar.getLayoutParams();
        param.height -= num;
        redbar.setLayoutParams(param);
    }

    //-----pen
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuinfo) {
        super.onCreateContextMenu(menu, v, menuinfo);
        if (v.getId() == R.id.pen_button) {
            getMenuInflater().inflate(R.menu.menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.blackpen:
                customView.penBlack();
                item.setChecked(true);
                return true;
            case R.id.redpen:
                customView.penRed();
                item.setChecked(true);
                return true;
            case R.id.yellowpen:
                customView.penyellow();
                item.setChecked(true);
                return true;
            case R.id.greenpen:
                customView.penGreen();
                item.setChecked(true);
                return true;
            case R.id.bluepen:
                customView.penBlue();
                item.setChecked(true);
                return true;
            case R.id.eraser:
                item.setChecked(true);
                customView.eraser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //-----animation
    public void startAnimation() {
        body = (ImageView) findViewById(R.id.body_view);
        AnimationDrawable anim = (AnimationDrawable) body.getDrawable();
        anim.start();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            startAnimation();
        }
    }


}
