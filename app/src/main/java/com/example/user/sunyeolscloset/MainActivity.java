package com.example.user.sunyeolscloset;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static com.example.user.sunyeolscloset.CustomView.getClothesData;

public class MainActivity extends AppCompatActivity {

    CustomView customView; // for Canvas
    ImageView body;
    //--menu
    ImageButton randombutton;
    ImageButton shopbutton;
    ImageButton penbutton;
    boolean penonoff = false;
    static TextView moneyview;

    static ImageView redbar; // for stress bar
    static TextView talkview; // for talking

    // for shopping
    public static CustomDialog cumtomdialog;
    public MyAdapter mAdapter;
    private Activity activity;

    static CustomSmallDialog customSmallDialog; // for the end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        customView = (CustomView) findViewById(R.id.custom_view);

        //--RandomBox
        randombutton = (ImageButton) findViewById(R.id.random_button);
        randombutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Random random = new Random();
                int randomItem = random.nextInt(10); // 0~9
                if (randomItem == 0 && !getClothesData(41).itemVisible) { // 아이템이 표시되지 않았다면
                    customView.setItemTrue(41); // 아이템 표시를 on
                    customView.invalidate();// 다시그리기
                    Toast.makeText(getApplicationContext(), "한복세트를 얻었다!", Toast.LENGTH_SHORT).show();
                    moneyview.setText(String.valueOf(Integer.parseInt((String) moneyview.getText()) - 3000));
                } else if (randomItem == 1 && !getClothesData(42).itemVisible) {
                    customView.setItemTrue(42);
                    customView.invalidate();
                    Toast.makeText(getApplicationContext(), "옛날 교복을 얻었다!", Toast.LENGTH_SHORT).show();
                    moneyview.setText(String.valueOf(Integer.parseInt((String) moneyview.getText()) - 3000));
                } else {
                    Toast.makeText(getApplicationContext(), "빈박스 사기당했다! -3000", Toast.LENGTH_SHORT).show();
                    moneyview.setText(String.valueOf(Integer.parseInt((String) moneyview.getText()) - 3000));
                    talkview.setText("멍청하기는");
                }
            }
        });

        //--Shop
        mAdapter = new MyAdapter(this);
        shopbutton = (ImageButton) findViewById(R.id.shop_button);
        shopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Custom Dialog
                cumtomdialog = new CustomDialog(activity, mAdapter);
                cumtomdialog.show();
            }
        });

        //--Pen
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
        registerForContextMenu(penbutton); //Long Click

        //-------stress
        redbar = (ImageView) findViewById(R.id.redbar);
        talkview = (TextView) findViewById(R.id.talkview);
        moneyview = (TextView) findViewById(R.id.moneyview);

        customSmallDialog = new CustomSmallDialog(this);
    }

    public static void stressUp(int i) {

        ViewGroup.LayoutParams param = redbar.getLayoutParams();
        param.height += i;
        if (param.height > 1200) { //스트레스지수 max 초과
            customSmallDialog.show();
            talkview.setText("꺼져");
        }
        redbar.setLayoutParams(param);
    }

    public static void stressDown(int i) {
        ViewGroup.LayoutParams param = redbar.getLayoutParams();
        param.height -= i;
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
            case R.id.eraser:
                item.setChecked(true);
                customView.eraser();
                return true;
            default:
                item.setChecked(true);
                customView.penColor(item.getItemId());
                return true;
        }
    }

    //-----body animation
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
