package com.example.lab3;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import java.io.FileInputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
    private final static String FILE_NAME = "styled_text.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        openText();
    }

    public void openText() {
        TextView textView = findViewById(R.id.tv_file);

        try (FileInputStream fis = openFileInput(FILE_NAME)) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String fileContent = new String(buffer);

            String[] parts = fileContent.split("\\|");

            String text = parts[0];
            String fontStyle = parts[1];


            switch (fontStyle) {
                case "NORMAL":
                    textView.setText(text);
                    textView.setTypeface(null, Typeface.NORMAL);
                    break;
                case "BOLD":
                    textView.setText(text);
                    textView.setTypeface(null, Typeface.BOLD);
                    break;
                case "ITALIC":
                    textView.setText(text);
                    textView.setTypeface(null, Typeface.ITALIC);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            textView.setText("File error.");
        }
    }
}
