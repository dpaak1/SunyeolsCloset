package com.example.user.sunyeolscloset;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends FrameLayout{
    public ItemView(Context context){
        super(context);
        init();
    }
    public ItemView(Context context, AttributeSet attrs){
        super(context,attrs);
        init();
    }

    ImageView iconView;
    TextView nameView, descView, priceView;
    ItemData mData;
    public void setItemData(ItemData data){
        mData = data;
        iconView.setImageResource(data.imageResourceID);
        nameView.setText(data.nameData);
        descView.setText(data.descData);
        priceView.setText(data.priceData);
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.list_item, this);
        iconView = (ImageView)findViewById(R.id.icon_view);
        nameView = (TextView)findViewById(R.id.item_name_view);
        descView = (TextView)findViewById(R.id.item_desc_view);
        priceView = (TextView)findViewById(R.id.item_price_view);
    }

}
