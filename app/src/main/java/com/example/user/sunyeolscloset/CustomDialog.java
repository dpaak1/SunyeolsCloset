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

import static com.example.user.sunyeolscloset.MainActivity.moneyview;
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

                if(a.nameData=="비타민D"){
                    Toast.makeText(activity, "비타민D로 선열이가 조금 밝아졌다" , Toast.LENGTH_SHORT).show();
                    talkview.setText("비타민D 합성이 중요해");
                    moneyview.setText( String.valueOf(Integer.parseInt((String) moneyview.getText())-1000));
                    stressDown(50);
                }else if(a.nameData=="양꼬치"){
                Toast.makeText(activity, " 무한리필 양꼬치로 스트레스가 풀렸다" , Toast.LENGTH_SHORT).show();
                talkview.setText("배부르다ㅋㅋ");
                    moneyview.setText( String.valueOf(Integer.parseInt((String) moneyview.getText())-13000));
                stressDown(200);
                }else if(a.nameData=="구두") {
                    CustomView.setItemTrue(11);
                    Toast.makeText(activity, " 검은 구두를 샀다" , Toast.LENGTH_SHORT).show();
                    moneyview.setText( String.valueOf(Integer.parseInt((String) moneyview.getText())-30000));
            }else if(a.nameData=="검은 셔츠") {
                CustomView.setItemTrue(31);
                    Toast.makeText(activity, " 검은 셔츠를 샀다" , Toast.LENGTH_SHORT).show();
                    moneyview.setText( String.valueOf(Integer.parseInt((String) moneyview.getText())-25000));
            }else if(a.nameData=="머리방울") {
            CustomView.setItemTrue(43);
             Toast.makeText(activity, " 화내면 어쩌지?" , Toast.LENGTH_SHORT).show();
                    moneyview.setText( String.valueOf(Integer.parseInt((String) moneyview.getText())-4000));
        }
            }
        });

        mAdapter = new MyAdapter(this.getContext());
        listView.setAdapter(mAdapter);
        setData(R.drawable.item_p,"비타민D","짜증지수 50 감소", "1000");
        setData(R.drawable.item_y,"양꼬치","짜증지수 200 감소", "13000");
        setData(R.drawable.item_bs,"구두","무난한 검은색 구두", "30000");
        setData(R.drawable.item_bst,"검은 셔츠","실패할 수 없는 검은 셔츠", "25000");
        setData(R.drawable.item_hair,"머리방울","선열이가 싫어할 것 같은 머리방울", "4000");
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
