package com.garrapeta.gameoflive;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;


public class GameOfLifeService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new MyWallpaperEngine();
    }

    /**
     * Wallpaper engine
     */
    private class MyWallpaperEngine extends Engine implements SurfaceHolderProvider {

        final GameOfLifeRenderer gameOfLifeRenderer;

        public MyWallpaperEngine() {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(GameOfLifeService.this);

            gameOfLifeRenderer = new GameOfLifeRenderer(prefs, this);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            gameOfLifeRenderer.onVisibilityChanged(visible);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            gameOfLifeRenderer.surfaceCreated(holder);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            gameOfLifeRenderer.surfaceDestroyed(holder);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format,
                                     int width, int height) {

            super.onSurfaceChanged(holder, format, width, height);
            gameOfLifeRenderer.surfaceChanged(holder, format, width, height);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            gameOfLifeRenderer.onTouchEvent(event);
        }

    }
}


