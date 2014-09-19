package com.garrapeta.gameoflive;


import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameOfLifeActivity extends Activity implements View.OnTouchListener, SurfaceHolderProvider, SurfaceHolder.Callback {

    private GameOfLife gameOfLifeCallback;
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_of_life);
        gameOfLifeCallback = new GameOfLife(PreferenceManager.getDefaultSharedPreferences(this), this);

        surfaceView = (SurfaceView) findViewById(R.id.game_of_life_surface);
        surfaceView.setOnTouchListener(this);
        surfaceView.getHolder().addCallback(this);


        gameOfLifeCallback.onVisibilityChanged(true);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gameOfLifeCallback.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    public SurfaceHolder getSurfaceHolder() {
        return surfaceView.getHolder();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        gameOfLifeCallback.surfaceCreated(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        gameOfLifeCallback.surfaceChanged(surfaceHolder, i, i2, i3);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        gameOfLifeCallback.surfaceDestroyed(surfaceHolder);
    }
}
