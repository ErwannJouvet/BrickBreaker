package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Adapter.LevelBaseAdapter;
import com.example.myapplication.Class.GameView;
import com.example.myapplication.Fragment.GameFragment;
import com.example.myapplication.Fragment.HomeFragment;
import com.example.myapplication.Fragment.LevelFragment;
import com.example.myapplication.Fragment.ResultFragment;
import com.example.myapplication.Interface.IGameListener;

import java.util.logging.Level;

public class MainActivity extends AppCompatActivity implements IGameListener {

    private HomeFragment homeFragment;
    private GameFragment gameFragment;
    private LevelFragment levelFragment;
    private ResultFragment resultFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        homeFragment = new HomeFragment();
        gameFragment = new GameFragment();
        levelFragment = new LevelFragment();
        resultFragment = new ResultFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, homeFragment)
                //.add(R.id.frameLayout, gameFragment)
                .add(R.id.frameLayout, levelFragment)
                .add(R.id.frameLayout, resultFragment)
                .hide(gameFragment)
                .hide(levelFragment)
                .hide(resultFragment)
                .commit();
        getSupportActionBar();
        homeFragment.setListener(this);
        gameFragment.setListener(this);
        resultFragment.setListener(this);
        levelFragment.setListener(this);
    }



    @Override
    public void onPlayGame(boolean play) {
        if (play && homeFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .hide(homeFragment)
                    .add(R.id.frameLayout, gameFragment)
                    .show(gameFragment)
                    .commit();
        }
        if (play && resultFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .hide(resultFragment)
                    .add(R.id.frameLayout, gameFragment)
                    .show(gameFragment)
                    .commit();
        }
    }

    //TODO : A AMELIORER
    @Override
    public void onSelectLevel(int level) {
        if(true){
            getSupportFragmentManager().beginTransaction()
                    .hide(levelFragment)
                    .add(R.id.frameLayout, gameFragment)
                    .show(gameFragment)
                    .commit();
        }
    }

    @Override
    public void onSelectionLevel(boolean selectionLevel){
        if(selectionLevel){
            getSupportFragmentManager().beginTransaction()
                    .hide(homeFragment)
                    .show(levelFragment)
                    .commit();
        }
        levelFragment.setListener(this);
    }

    @Override
    public void onGameOver(boolean over, boolean result, int score) {
        resultFragment.setScore(String.valueOf(score));
        if(result) {
            resultFragment.setResultat("Gagné !");
        } else {
            resultFragment.setResultat("Perdu...");
        }
        if(over) {
            getSupportFragmentManager().beginTransaction()
                    .remove(gameFragment)
                    .show(resultFragment)
                    .commit();
        }
        //TextView textResult = (TextView) findViewById(R.id.textViewScoreResult);
        //textResult.setText("prout");
    }

    @Override
    public void onHome() {
        getSupportFragmentManager().beginTransaction()
                .hide(resultFragment)
                .show(homeFragment)
                .commit();
    }
}