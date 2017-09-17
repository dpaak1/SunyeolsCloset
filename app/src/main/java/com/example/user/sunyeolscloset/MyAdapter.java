package com.example.user.sunyeolscloset;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter{

    ArrayList<ItemData> items = new ArrayList<ItemData>();
    Context mContext;

    public MyAdapter(Context context){
        mContext = context;
    }

    public void add(ItemData item){
        items.add(item);
        notifyDataSetChanged();//다시 그려라
    }
    public void addAll(List<ItemData> items){
        this.items.addAll(items);
        notifyDataSetChanged();//다시 그려라
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemView v;
        if(convertView == null){
            v = new ItemView(mContext);
        }else{
            v = (ItemView)convertView;
        }
        v.setItemData(items.get(position));
        return v;
    }

}
