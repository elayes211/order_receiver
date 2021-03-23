package com.example.kontanaks;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.File;

public class FirstFragment extends Fragment {
    Integer tables = 12;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        colorizeButtons(this.getView());
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.selectedPage = 2;
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        Button synoloButton = view.findViewById(R.id.synolo);
        synoloButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Resume.class);
                startActivity(intent);
            }});

        colorizeButtons(this.getView());

        Button tableButton = view.findViewById(R.id.button00);
        initializeButtonClicks(R.id.button00, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button01);
        initializeButtonClicks(R.id.button01, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button02);
        initializeButtonClicks(R.id.button02, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button03);
        initializeButtonClicks(R.id.button03, tableButton.getText().toString(), view);

        tableButton = view.findViewById(R.id.button04);
        initializeButtonClicks(R.id.button04, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button05);
        initializeButtonClicks(R.id.button05, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button06);
        initializeButtonClicks(R.id.button06, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button07);
        initializeButtonClicks(R.id.button07, tableButton.getText().toString(), view);

        tableButton = view.findViewById(R.id.button08);
        initializeButtonClicks(R.id.button08, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button09);
        initializeButtonClicks(R.id.button09, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button010);
        initializeButtonClicks(R.id.button010, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button011);
        initializeButtonClicks(R.id.button011, tableButton.getText().toString(), view);

    }


    public void initializeButtonClicks(Integer buttonResourceId, String tableId, View view) {
        final Button button = view.findViewById(buttonResourceId);
        final String tableId2 = tableId;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditTable.class);
                    intent.putExtra("tableId", tableId2);
                //writeToFile(readFromFile(a1.getContext()) + "\n" + "A1: " + "\n", a1.getContext());
                startActivity(intent);
            }});


    };


    public void colorizeButtons(View view) {

        Button button;
        String buttonText = "";
        for (int i = 0; i < tables; i++) {
            switch (i) {
                case 0:
                    button = view.findViewById(R.id.button00);
                    buttonText = button.getText().toString();
                    break;
                case 1:
                    button = view.findViewById(R.id.button01);
                    buttonText = button.getText().toString();
                    break;
                case 2:
                    button = view.findViewById(R.id.button02);
                    buttonText = button.getText().toString();
                    break;
                case 3:
                    button = view.findViewById(R.id.button03);
                    buttonText = button.getText().toString();
                    break;
                case 4:
                    button = view.findViewById(R.id.button04);
                    buttonText = button.getText().toString();
                    break;
                case 5:
                    button = view.findViewById(R.id.button05);
                    buttonText = button.getText().toString();
                    break;
                case 6:
                    button = view.findViewById(R.id.button06);
                    buttonText = button.getText().toString();
                    break;
                case 7:
                    button = view.findViewById(R.id.button07);
                    buttonText = button.getText().toString();
                    break;
                case 8:
                    button = view.findViewById(R.id.button08);
                    buttonText = button.getText().toString();
                    break;
                case 9:
                    button = view.findViewById(R.id.button09);
                    buttonText = button.getText().toString();
                    break;
                case 10:
                    button = view.findViewById(R.id.button010);
                    buttonText = button.getText().toString();
                    break;
                case 11:
                    button = view.findViewById(R.id.button011);
                    buttonText = button.getText().toString();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + i);
            }

            Drawable roundDrawable = getResources().getDrawable(R.drawable.shape);


            if (fileExists(buttonText))
                roundDrawable.setColorFilter(Color.rgb(17, 135, 214), PorterDuff.Mode.SRC_ATOP);
            else if (fileExists(buttonText + "_served"))
                roundDrawable.setColorFilter(Color.rgb(255, 255, 0), PorterDuff.Mode.SRC_ATOP);
            else
                roundDrawable.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);

            if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                button.setBackgroundDrawable(roundDrawable);
            } else {
                button.setBackground(roundDrawable);
            }
        }
    }

    public boolean fileExists(String fname) {
        File file = getActivity().getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

}