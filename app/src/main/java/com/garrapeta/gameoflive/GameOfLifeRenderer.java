package com.garrapeta.gameoflive;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class GameOfLifeRenderer {


    private static final int SPACING = 50;
    private static final long RENDER_DELAY = 200;

    private SurfaceHolderProvider surfaceHolderProvider;
    private Paint paint = new Paint();
    private boolean visible = true;
    private boolean touchEnabled;
    private final Handler handler = new Handler();
    private final Runnable drawRunner;

    private final GameOfLifeWorld world;
    private boolean isPlaying = true;

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
                processFrame();
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
        if (event.getAction() == MotionEvent.ACTION_UP) {
            world.onCellClicked((int) (event.getX() / SPACING), (int) (event.getY() / SPACING));
            processFrame();
        }
    }

    public void setPlay(boolean playing) {
        this.isPlaying = playing;
    }

    private void processFrame() {
        evolveWorld();
        drawWorld();
    }

    private void evolveWorld() {
        if (isPlaying) {
            world.step();
        }
    }

    private void drawWorld() {
        SurfaceHolder holder = surfaceHolderProvider.getSurfaceHolder();
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas();
            if (canvas != null) {
                drawWorld(canvas);
            }
        } finally {
            if (canvas != null)
                holder.unlockCanvasAndPost(canvas);
        }
        handler.removeCallbacks(drawRunner);
        if (visible) {
            handler.postDelayed(drawRunner, RENDER_DELAY);
        }
    }

    private void drawWorld(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        drawGrid(canvas);
        drawCells(canvas);
        drawNeighbours(canvas);
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

    private void drawCells(Canvas canvas) {
        paint.setColor(Color.CYAN);
        paint.setStyle(Paint.Style.FILL);

        int cols = world.getCols();
        int rows = world.getRows();

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                drawCell(canvas, i, j);
            }
        }
    }

    private void drawCell(Canvas canvas, int x, int y) {
        boolean alive = world.isAlive(x, y);
        if (alive) {
            canvas.drawRect(x * SPACING, y * SPACING, (x + 1) * SPACING, (y + 1) * SPACING, paint);
        }
    }

    private void drawNeighbours(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setTextSize(SPACING / 2);

        int cols = world.getCols();
        int rows = world.getRows();

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                drawNeighbours(canvas, i, j);
            }
        }
    }

    private void drawNeighbours(Canvas canvas, int x, int y) {
        int count = world.getAliveNeighbours(x, y);
        canvas.drawText(String.valueOf(count), x * SPACING, y * SPACING, paint);
    }

}
