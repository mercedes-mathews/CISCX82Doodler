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
    private List<DrawPath> paths;       // List to store paths and their paints
    private List<DrawPath> redoPaths;  // List to store paths for redo

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize the lists
        paths = new ArrayList<>();
        redoPaths = new ArrayList<>();

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
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentPath = new Path();
                currentPath.moveTo(x, y);
                break;

            case MotionEvent.ACTION_MOVE:
                currentPath.lineTo(x, y);
                break;

            case MotionEvent.ACTION_UP:
                // Save the current path and paint to the list
                paths.add(new DrawPath(currentPath, currentPaint));
                currentPath = new Path();
                currentPaint = new Paint(currentPaint);

                // Clear the redo stack since a new path is drawn
                redoPaths.clear();
                break;
        }

        invalidate(); // Redraw the view
        return true;
    }

    // Method to undo the last path
    public void undo() {
        if (!paths.isEmpty()) {
            // Move the last path to the redo stack
            redoPaths.add(paths.remove(paths.size() - 1));
            invalidate(); // Redraw the view
        }
    }

    // Method to redo the last undone path
    public void redo() {
        if (!redoPaths.isEmpty()) {
            // Move the last path from redo stack back to paths
            paths.add(redoPaths.remove(redoPaths.size() - 1));
            invalidate(); // Redraw the view
        }
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
        paths.clear();
        redoPaths.clear(); // Clear redo stack as well
        currentPath.reset();
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
