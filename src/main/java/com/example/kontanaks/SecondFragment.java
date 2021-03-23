package com.example.kontanaks;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.File;

public class SecondFragment extends Fragment {
    Integer tables = 12;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        colorizeButtons(this.getView());
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.selectedPage = 1;
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        Button synoloButton = view.findViewById(R.id.synolo);
        synoloButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Resume.class);
                startActivity(intent);
            }});

        Button tableButton = view.findViewById(R.id.button10);
        initializeButtonClicks(R.id.button10, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button11);
        initializeButtonClicks(R.id.button11, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button12);
        initializeButtonClicks(R.id.button12, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button13);
        initializeButtonClicks(R.id.button13, tableButton.getText().toString(), view);

        tableButton = view.findViewById(R.id.button14);
        initializeButtonClicks(R.id.button14, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button15);
        initializeButtonClicks(R.id.button15, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button16);
        initializeButtonClicks(R.id.button16, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button17);
        initializeButtonClicks(R.id.button17, tableButton.getText().toString(), view);

        tableButton = view.findViewById(R.id.button18);
        initializeButtonClicks(R.id.button18, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button19);
        initializeButtonClicks(R.id.button19, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button110);
        initializeButtonClicks(R.id.button110, tableButton.getText().toString(), view);
        tableButton = view.findViewById(R.id.button111);
        initializeButtonClicks(R.id.button111, tableButton.getText().toString(), view);

        colorizeButtons(this.getView());
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
                    button = view.findViewById(R.id.button10);
                    buttonText = button.getText().toString();
                    break;
                case 1:
                    button = view.findViewById(R.id.button11);
                    buttonText = button.getText().toString();
                    break;
                case 2:
                    button = view.findViewById(R.id.button12);
                    buttonText = button.getText().toString();
                    break;
                case 3:
                    button = view.findViewById(R.id.button13);
                    buttonText = button.getText().toString();
                    break;
                case 4:
                    button = view.findViewById(R.id.button14);
                    buttonText = button.getText().toString();
                    break;
                case 5:
                    button = view.findViewById(R.id.button15);
                    buttonText = button.getText().toString();
                    break;
                case 6:
                    button = view.findViewById(R.id.button16);
                    buttonText = button.getText().toString();
                    break;
                case 7:
                    button = view.findViewById(R.id.button17);
                    buttonText = button.getText().toString();
                    break;
                case 8:
                    button = view.findViewById(R.id.button18);
                    buttonText = button.getText().toString();
                    break;
                case 9:
                    button = view.findViewById(R.id.button19);
                    buttonText = button.getText().toString();
                    break;
                case 10:
                    button = view.findViewById(R.id.button110);
                    buttonText = button.getText().toString();
                    break;
                case 11:
                    button = view.findViewById(R.id.button111);
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