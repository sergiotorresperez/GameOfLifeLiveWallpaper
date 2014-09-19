package com.garrapeta.gameoflive;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ToggleButton;

public class GameOfLifeActivity extends Activity implements View.OnTouchListener, GameOfLifeRenderer.SurfaceHolderProvider, SurfaceHolder.Callback {

    private GameOfLifeRenderer gameOfLifeRenderer;
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_of_life);
        gameOfLifeRenderer = new GameOfLifeRenderer(this, this);

        surfaceView = (SurfaceView) findViewById(R.id.game_of_life_surface);
        surfaceView.setOnTouchListener(this);
        surfaceView.getHolder().addCallback(this);


        gameOfLifeRenderer.onVisibilityChanged(true);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem playPauseItem = menu.findItem(R.id.playpause);
        if (gameOfLifeRenderer.isPlaying()) {
            playPauseItem.setTitle("Pause");
        } else {
            playPauseItem.setTitle("Play");
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.playpause:
                onPlayPauseClicked();
                return true;
            case R.id.preferences:
                onPreferencesClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PreferencesActivity.REQUEST_CODE) {
            gameOfLifeRenderer.setConfiguration(this);
        }
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


    private void onPlayPauseClicked() {
        gameOfLifeRenderer.setPlaying(!gameOfLifeRenderer.isPlaying());
        invalidateOptionsMenu();
    }

    private void onPreferencesClicked() {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivityForResult(intent, PreferencesActivity.REQUEST_CODE);
    }

}
