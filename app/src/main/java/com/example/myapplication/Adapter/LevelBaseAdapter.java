package com.example.myapplication.Adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Interface.IGameListener;
import com.example.myapplication.R;
import com.example.myapplication.Class.Level;

import java.util.ArrayList;

public class LevelBaseAdapter extends BaseAdapter {

    private ArrayList<Level> levels;
    private Context context;
    private IGameListener listener;

    private Button button;

    public void setListener(IGameListener listener){
        this.listener = listener;
    }

    public LevelBaseAdapter(ArrayList<Level> levels, Context context) {
        this.levels = levels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return levels.size();
    }

    @Override
    public Object getItem(int i) {
        return levels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_level, viewGroup,false);
        }
        Button button = view.findViewById(R.id.buttonLevelSelected);
        button.setText(levels.get(i).getLevel());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonLevel = button.getText().toString();
                char numberLevel = buttonLevel.charAt(buttonLevel.length()-1);
                listener.onSelectLevel(numberLevel);
                System.out.println(numberLevel);
            }
        });

        return view;
    }

}
