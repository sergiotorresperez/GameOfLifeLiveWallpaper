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

public class CirclesRenderer {


    public class MyPoint {
        String text;
        final int x;
        final int y;

        public MyPoint(String text, int x, int y) {
            this.text = text;
            this.x = x;
            this.y = y;
        }
    }



    private SurfaceHolderProvider surfaceHolderProvider;
    private List<MyPoint> circles;
    private Paint paint = new Paint();
    private int width;
    int height;
    private boolean visible = true;
    private int maxNumber;
    private boolean touchEnabled;
    private final Handler handler = new Handler();

    private final Runnable drawRunner;

    public CirclesRenderer(SharedPreferences prefs, SurfaceHolderProvider surfaceHolderProvider) {
        this.surfaceHolderProvider = surfaceHolderProvider;
        maxNumber = Integer
                .valueOf(prefs.getString("numberOfCircles", "4"));
        touchEnabled = prefs.getBoolean("touch", false);
        circles = new ArrayList<MyPoint>();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(10f);

        drawRunner = new Runnable() {
            @Override
            public void run() {
                Log.i("stp", "frame");
                draw();
            }
        };

        handler.post(drawRunner);
    }

    public void onVisibilityChanged(boolean visible) {
        this.visible = visible;
        if (visible) {
            handler.post(drawRunner);
        } else {
            handler.removeCallbacks(drawRunner);
        }
    }


    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.visible = false;
        handler.removeCallbacks(drawRunner);
    }

    public void onTouchEvent(MotionEvent event) {
        if (touchEnabled) {

            float x = event.getX();
            float y = event.getY();
            SurfaceHolder holder = surfaceHolderProvider.getSurfaceHolder();
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.BLACK);
                    circles.clear();
                    circles.add(new MyPoint(String.valueOf(circles.size() + 1), (int) x, (int) y));
                    drawCircles(canvas, circles);

                }
            } finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void draw() {
        Log.i("stp", "draw");
        SurfaceHolder holder = surfaceHolderProvider.getSurfaceHolder();
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas();
            if (canvas != null) {

                if (circles.size() >= maxNumber) {
                    circles.clear();
                }
                int x = (int) (width * Math.random());
                int y = (int) (height * Math.random());
                Log.i("stp", "creating point at " + x + " " + y);
                circles.add(new MyPoint(String.valueOf(circles.size() + 1),
                        x, y));
                drawCircles(canvas, circles);
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

    // Surface view requires that all elements are drawn completely
    private void drawCircles(Canvas canvas, List<MyPoint> circles) {
        canvas.drawColor(Color.BLACK);
        for (MyPoint point : circles) {
//            Log.d("stp", "circle");
            canvas.drawCircle(point.x, point.y, 20.0f, paint);
        }
    }
}
