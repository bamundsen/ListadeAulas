package com.example.listadeaulas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.listadeaulas.R;
import com.example.listadeaulas.data.Class;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<Class> classes = new ArrayList<>();

    public ListAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return classes.size();
    }

    @Override
    public Class getItem(int position) {
        return classes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return classes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_adapter, parent, false);

        TextView txtClass = v.findViewById(R.id.txtClass);
        TextView txtMatter = v.findViewById(R.id.txtMatter);
        ImageView itmIcon = v.findViewById(R.id.itmIcon);

        Class c = classes.get(position);
        txtClass.setText(c.getTitle());
        txtMatter.setText(c.getMatter());
        if (c.getUrl().startsWith("https://www.youtube") || c.getUrl().startsWith("http://www.youtube")
            || c.getUrl().startsWith("https://youtube") || c.getUrl().startsWith("http://youtube")) {
            itmIcon.setImageResource(R.drawable.ic_video);
        }
        else {
            itmIcon.setImageResource(R.drawable.ic_browser);
        }

        return v;
    }

    public void setItemsList(List<Class> classes){
        this.classes = classes;
        if (classes.get(0) == null)
            System.out.println("nulo");
        else
            System.out.println(classes.get(0));
        notifyDataSetChanged();
    }
}