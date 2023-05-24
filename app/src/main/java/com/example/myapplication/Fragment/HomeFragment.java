package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.Class.GameView;
import com.example.myapplication.Interface.IGameListener;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements IGameListener, View.OnClickListener {

    private Button buttonPlay;
    private Button buttonLevel;
    private IGameListener listener;

    public void setListener(IGameListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        buttonPlay = view.findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);
        buttonLevel = view.findViewById(R.id.buttonLevel);
        buttonLevel.setOnClickListener(this);

        return view;
    }

    @Override
    public void onPlayGame(boolean play) {
        play = false;
    }

    @Override
    public void onSelectionLevel(boolean selectionLevel){
        selectionLevel = false;
    }

    @Override
    public void onSelectLevel(int level) {

    }

    @Override
    public void onGameOver(boolean over, boolean result, int score) {

    }

    @Override
    public void onHome() {

    }

    @Override
    public void onClick(View view) {
        if(listener != null) {
            if(view.equals(buttonPlay)){
                listener.onPlayGame(true);
            } else {
                listener.onSelectionLevel(true);
                System.out.println("Sélection d'un niveau");
            }
        }
    }
}