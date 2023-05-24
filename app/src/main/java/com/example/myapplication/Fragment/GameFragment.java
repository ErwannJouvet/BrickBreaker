package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Class.GameView;
import com.example.myapplication.Interface.IGameListener;
import com.example.myapplication.Interface.IGameviewListener;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class GameFragment extends Fragment implements IGameListener, IGameviewListener {

    private IGameListener listener;
    private boolean isGameOver = false;
    private GameView gameView = null;

    public void setListener(IGameListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);


        view.invalidate();
        GameView.deleteInstance();

        gameView = GameView.getInstance(getContext());

        gameView.setGameviewListener(this);

        return gameView;
    }

/*    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            if (gameView != null)
                gameView.invalidate();
            onCreate(null);
        }
    }*/

    @Override
    public void onPlayGame(boolean play) {

    }

    @Override
    public void onSelectLevel(int level) {

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

    @Override
    public void onGameviewOver(boolean result, int score) {
        if (listener != null) {
            listener.onGameOver(true, result, score);
            this.isGameOver = true;
            gameView.invalidate();
        }
    }
}