package com.garrapeta.gameoflive;


import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ToggleButton;

public class GameOfLifeActivity extends Activity implements View.OnTouchListener, SurfaceHolderProvider, SurfaceHolder.Callback {

    private GameOfLifeRenderer gameOfLifeRenderer;
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_of_life);
        gameOfLifeRenderer = new GameOfLifeRenderer(PreferenceManager.getDefaultSharedPreferences(this), this);

        surfaceView = (SurfaceView) findViewById(R.id.game_of_life_surface);
        surfaceView.setOnTouchListener(this);
        surfaceView.getHolder().addCallback(this);


        gameOfLifeRenderer.onVisibilityChanged(true);

        final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.playPauseButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameOfLifeRenderer.setPlay(toggleButton.isChecked());
            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gameOfLifeRenderer.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    public SurfaceHolder getSurfaceHolder() {
        return surfaceView.getHolder();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        gameOfLifeRenderer.surfaceCreated(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        gameOfLifeRenderer.surfaceChanged(surfaceHolder, i, i2, i3);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        gameOfLifeRenderer.surfaceDestroyed(surfaceHolder);
    }
}
