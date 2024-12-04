package com.example.doodlecisc482;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DoodleView doodleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the DoodleView
        doodleView = findViewById(R.id.doodleView);

        // Find and set up the Refresh Button
        Button refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(v -> doodleView.clearCanvas());

        // Set up the Brush Size SeekBar
        SeekBar brushSizeSeekBar = findViewById(R.id.brushSizeSeekBar);
        brushSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                doodleView.setBrushSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not used
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not used
            }
        });

        // Set up the Opacity SeekBar
        SeekBar opacitySeekBar = findViewById(R.id.opacitySeekBar);
        opacitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                doodleView.setBrushOpacity(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not used
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not used
            }
        });

        // Set up the Color Spinner
        Spinner colorSpinner = findViewById(R.id.colorSpinner);
        colorSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                String selectedColor = parent.getItemAtPosition(position).toString();
                switch (selectedColor) {
                    case "Red":
                        doodleView.setBrushColor(Color.RED);
                        break;
                    case "Blue":
                        doodleView.setBrushColor(Color.BLUE);
                        break;
                    case "Green":
                        doodleView.setBrushColor(Color.GREEN);
                        break;
                    case "Yellow":
                        doodleView.setBrushColor(Color.YELLOW);
                        break;
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                // Not used
            }
        });
    }
}
