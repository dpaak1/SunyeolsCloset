package com.example.user.sunyeolscloset;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.user.sunyeolscloset.MainActivity.stressDown;
import static com.example.user.sunyeolscloset.MainActivity.talkview;

public class CustomDialog extends Dialog {

    private Activity activity;
    public ImageView titleView;
    public ListView listView; // 리스트뷰
    ImageButton closeButton;

    MyAdapter mAdapter;

    //private String title;// 상단 타이틀 내용
   // private View.OnClickListener checkBtListener; // 버튼 리스너
  //  private Button closeBt; // 닫기 버튼
   // private TextView dialogTitle; // 상단 타이틀뷰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 메인 layout
        setContentView(R.layout.custom_listview_dialog);
        listView = (ListView) findViewById(R.id.bag_listview);
        titleView = (ImageView) findViewById(R.id.bag_title);
        closeButton = (ImageButton) findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemData a = new ItemData();
                a = (ItemData)mAdapter.getItem(position);
                Toast.makeText(activity, a.nameData , Toast.LENGTH_SHORT).show();
                talkview.setText("ㅇㅅㅇ");
                stressDown(100);
            }
        });

        mAdapter = new MyAdapter(this.getContext());
        listView.setAdapter(mAdapter);
        setData(R.drawable.item_p,"비타민D","짜증지수 50 감소", "1000");
        setData(R.drawable.item_y,"양꼬치","짜증지수 200 감소", "13000");
    }

    private void setData(int imgID, String name, String desc, String price){
            ItemData d = new ItemData();
             d.imageResourceID =imgID;
            d.nameData = name;
            d.descData = desc;
            d.priceData = price;
            mAdapter.add(d);
        }

    public CustomDialog(Activity activity, MyAdapter listAdapter) { //생성자

        super(activity, android.R.style.Theme_Translucent_NoTitleBar);
        this.activity = activity;
        this.mAdapter = listAdapter;

    }

}
