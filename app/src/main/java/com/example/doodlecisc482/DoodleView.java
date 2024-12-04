package com.example.doodlecisc482;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DoodleView extends View {
    private Paint currentPaint;
    private Path currentPath;
    private List<DrawPath> paths; // List to store paths and their paints

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize the list of paths
        paths = new ArrayList<>();

        // Initialize the current Paint object
        currentPaint = createNewPaint(Color.BLACK, 10, 255);

        // Initialize the current Path object
        currentPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw all saved paths with their respective paints
        for (DrawPath drawPath : paths) {
            canvas.drawPath(drawPath.path, drawPath.paint);
        }

        // Draw the current path being drawn
        canvas.drawPath(currentPath, currentPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(); // X-coordinate of touch
        float y = event.getY(); // Y-coordinate of touch

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // Start of a touch
                currentPath = new Path(); // Create a new path for this touch
                currentPath.moveTo(x, y);
                break;

            case MotionEvent.ACTION_MOVE: // Move while touching
                currentPath.lineTo(x, y);
                break;

            case MotionEvent.ACTION_UP: // Lift up
                // Save the current path and paint to the list
                paths.add(new DrawPath(currentPath, currentPaint));
                currentPath = new Path(); // Reset for the next touch
                currentPaint = new Paint(currentPaint); // Clone the current paint for future use
                break;
        }

        invalidate(); // Redraw the view
        return true;
    }

    // Method to set brush color
    public void setBrushColor(int color) {
        currentPaint.setColor(color);
    }

    // Method to set brush size
    public void setBrushSize(float size) {
        currentPaint.setStrokeWidth(size);
    }

    // Method to set brush opacity
    public void setBrushOpacity(int alpha) {
        currentPaint.setAlpha(alpha);
    }

    // Method to clear the canvas
    public void clearCanvas() {
        paths.clear(); // Clear all saved paths
        currentPath.reset(); // Clear the current path
        invalidate();
    }

    // Helper method to create a new Paint object
    private Paint createNewPaint(int color, float strokeWidth, int alpha) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setAntiAlias(true);
        paint.setAlpha(alpha);
        return paint;
    }

    // Inner class to store a path and its corresponding paint
    private static class DrawPath {
        Path path;
        Paint paint;

        DrawPath(Path path, Paint paint) {
            this.path = path;
            this.paint = new Paint(paint); // Clone the paint to avoid shared state
        }
    }
}
