package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Interface.IGameListener;
import com.example.myapplication.R;

public class ResultFragment extends Fragment implements View.OnClickListener {

    private TextView textResult;
    private TextView textScore;
    private String score = "0";
    private String resultat;
    private Button buttonMenu;
    private Button buttonReplay;

    private IGameListener listener;

    public void setListener(IGameListener listener) {
        this.listener = listener;
    }
    public void setResultat(String resultat) {
        this.resultat = resultat;
        textResult.setText(this.resultat);
        textResult.invalidate();

    }

    public void setScore(String score) {
        this.score = score;
        textScore.setText(this.score);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        textResult = view.findViewById(R.id.textViewScoreResult);
        textScore = view.findViewById(R.id.textViewScore);
        buttonMenu = view.findViewById(R.id.buttonMenu);
        buttonReplay = view.findViewById(R.id.buttonRejouer);
        buttonMenu.setOnClickListener(this);
        buttonReplay.setOnClickListener(this);
        //Log.e("textResult", "this result est égal à : "+this.resultat);
        return view;
    }



    @Override
    public void onClick(View view) {
        if (listener != null) {
            if(view.equals(buttonReplay)) {
                listener.onPlayGame(true);
            }
            else if(view.equals(buttonMenu)) {
                listener.onHome();
            }
        }
    }
}