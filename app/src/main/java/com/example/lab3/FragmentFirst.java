package com.example.lab3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.graphics.Typeface;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Intent;


public class FragmentFirst extends Fragment {

    private OnTextSubmitListener callback;
    private static final String FILE_NAME = "styled_text.txt";

    public interface OnTextSubmitListener {
        void onTextSubmitted(String text, int fontStyle);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnTextSubmitListener) {
            callback = (OnTextSubmitListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnTextSubmitListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        EditText editText = view.findViewById(R.id.text_edit);
        Button buttonOk = view.findViewById(R.id.button_ok);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup_fonts);
        Button buttonOpenSecond = view.findViewById(R.id.button_open);

        buttonOk.setOnClickListener(v -> {
            String text = editText.getText().toString();
            if (text.isEmpty()) {
                Toast.makeText(getActivity(), "Nothing entered", Toast.LENGTH_SHORT).show();
                editText.setError("Field cannot be empty");
            } else {
                int fontStyle = Typeface.NORMAL;
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.rb_bold) {
                    fontStyle = Typeface.BOLD;
                } else if (selectedId == R.id.rb_italic) {
                    fontStyle = Typeface.ITALIC;
                }

                saveToInternalStorage(text, fontStyle);
                callback.onTextSubmitted(text, fontStyle);
            }
        });

        buttonOpenSecond.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SecondActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void saveToInternalStorage(String text, int fontStyle) {
        String styleString = fontStyle == Typeface.BOLD ? "BOLD" :
                fontStyle == Typeface.ITALIC ? "ITALIC" : "NORMAL";
        String content = text + "|" + styleString;

        FileOutputStream fos = null;
        try {
            fos = getActivity().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            Toast.makeText(getActivity(), "File saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "File error", Toast.LENGTH_SHORT).show();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
