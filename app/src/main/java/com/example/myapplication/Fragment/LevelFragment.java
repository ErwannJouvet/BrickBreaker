package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Adapter.LevelBaseAdapter;
import com.example.myapplication.Class.Level;
import com.example.myapplication.Interface.IGameListener;
import com.example.myapplication.R;

import java.sql.Array;
import java.util.ArrayList;


public class LevelFragment extends Fragment implements IGameListener, View.OnClickListener{

    private ListView listViewLevel;
    private IGameListener listener;

    public void setListener(IGameListener listener){
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level, container, false);
        listViewLevel = view.findViewById(R.id.listViewLevel);

        ArrayList<Level> levels = new ArrayList<Level>();
        levels.add(new Level("NIVEAU 1"));
        levels.add(new Level("NIVEAU 2"));
        levels.add(new Level("NIVEAU 3"));

        LevelBaseAdapter adapter = new LevelBaseAdapter(levels,getContext());
        listViewLevel.setAdapter(adapter);
        adapter.setListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){

        }
    }

    @Override
    public void onPlayGame(boolean play) {

    }

    @Override
    public void onSelectLevel(int level) {
        if(listener != null){
            listener.onSelectLevel(level);
        }
    }

    @Override
    public void onSelectionLevel(boolean selectionLevel) {

    }

    @Override
    public void onGameOver(boolean over, boolean result, int score) {

    }

    @Override
    public void onHome() {

    }


}
