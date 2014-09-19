package com.garrapeta.gameoflive;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;

public class GameOfLifeRenderer {


    private static final int SPACING = 50;

    private SurfaceHolderProvider surfaceHolderProvider;
    private Paint paint = new Paint();
    private boolean visible = true;
    private boolean touchEnabled;
    private final Handler handler = new Handler();
    private final Runnable drawRunner;

    private final GameOfLifeWorld world;

    public GameOfLifeRenderer(SharedPreferences prefs, SurfaceHolderProvider surfaceHolderProvider) {
        this.surfaceHolderProvider = surfaceHolderProvider;
        touchEnabled = prefs.getBoolean("touch", false);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(1f);

        world = new GameOfLifeWorld();

        drawRunner = new Runnable() {
            @Override
            public void run() {
                Log.i("stp", "frame");
                draw();
            }
        };

        handler.post(drawRunner);
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }


    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        world.createMatrix(width / SPACING, height / SPACING);
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.visible = false;
        handler.removeCallbacks(drawRunner);
    }

    public void onVisibilityChanged(boolean visible) {
        this.visible = visible;
        if (visible) {
            handler.post(drawRunner);
        } else {
            handler.removeCallbacks(drawRunner);
        }
    }

    public void onTouchEvent(MotionEvent event) {
        if (!touchEnabled) {
        }
        return;
    }

    private void draw() {
        SurfaceHolder holder = surfaceHolderProvider.getSurfaceHolder();
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas();
            if (canvas != null) {
                draw(canvas);
            }
        } finally {
            if (canvas != null)
                holder.unlockCanvasAndPost(canvas);
        }
        handler.removeCallbacks(drawRunner);
        if (visible) {
            handler.postDelayed(drawRunner, 2000);
        }
    }

    private void draw(Canvas canvas) {
        Log.i("stp", "draw in canvas");
        canvas.drawColor(Color.BLACK);
        drawGrid(canvas);
    }

    private void drawGrid(Canvas canvas) {
        paint.setColor(Color.GRAY);

        int gridHeight = world.getRows() * SPACING;
        int gridWidth = world.getCols() * SPACING;

        for (int i = 0; i < world.getCols(); i ++) {
            int startX = i * SPACING;
            canvas.drawLine(startX, 0, startX, gridHeight, paint);
        }

        for (int j = 0; j < world.getRows(); j ++) {
            int startY = j * SPACING;
            canvas.drawLine(0, startY, gridWidth, startY, paint);
        }
    }

}
