package com.garrapeta.gameoflive;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;


public class GameOfLifeService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new MyWallpaperEngine();
    }

    /**
     * Wallpaper engine
     */
    private class MyWallpaperEngine extends Engine implements SurfaceHolderProvider {

        final GameOfLife gameOfLife;

        public MyWallpaperEngine() {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(GameOfLifeService.this);

            gameOfLife = new GameOfLife(prefs, this);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            gameOfLife.onVisibilityChanged(visible);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            gameOfLife.surfaceCreated(holder);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            gameOfLife.surfaceDestroyed(holder);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format,
                                     int width, int height) {

            super.onSurfaceChanged(holder, format, width, height);
            gameOfLife.surfaceChanged(holder, format, width, height);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            gameOfLife.onTouchEvent(event);
        }

    }
}


