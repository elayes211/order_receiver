package com.example.kontanaks;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kontanaks.etc.Table;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Resume extends AppCompatActivity {


    int editId = R.id.editTextTextMultiLine3;
    Integer tables = 24;
    List<Table> tableList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume_main);

        if (tableList == null)
        {
            tableList = new ArrayList<Table>(tables);
            retrieveAllTables();
        }

        final EditText edit = findViewById(editId);
        edit.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_FLAG_MULTI_LINE |
                InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        edit.setFocusable(false);
        ViewGroup.LayoutParams layoutParams = edit.getLayoutParams();
        DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;


            layoutParams.height -= 64;

            if(hasNavigationBar(this)) {
                // Do whatever you need to do, this device has a soft Navigation Bar
                layoutParams.height -= 200;
            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.rgb(61, 133, 198));
        }

        try {
            getProductNamesAndPrice();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean hasNavigationBar(Activity activity) {
        Rect rectangle = new Rect();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels != (rectangle.top + rectangle.height());
    }

    protected void getProductNamesAndPrice() throws Exception {
        final EditText edit = findViewById(editId);
        Table table_temp = new Table();
        for (int i=0; i < getCapacity(tableList); i++) {
            table_temp.addProducts(tableList.get(i).getProducts());
        }

        final Button reset = findViewById(R.id.reset2);
        if (table_temp.getPrice() > 0)
            reset.setText(" (" + table_temp.getPrice() + " €) ΑΠΟΣΤΟΛΗ ");
        else
            reset.setText(" ΑΠΟΣΤΟΛΗ ");

        edit.setText(table_temp.getSerializedProducts());
    }



    private void retrieveAllTables() {
        if (fileExist("A2"))
        Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();

        for (int i = 1; i <= tables/3; i++) {
                tableList.add(retrieveTable("A" + i));
        }
        for (int i = 1; i <= tables/3; i++) {
                tableList.add(retrieveTable("B" + i));
        }
        for (int i = 1; i <= tables/3; i++) {
                tableList.add(retrieveTable("C" + i));
        }
    }

    public void reset2(View v) {

        // TODO: Change the color of served tables to yellow and print the tableId instead of just the products
/*        for (int i = 1; i <= tables/3; i++) {
            renameFile("A" + i, "A" + i + "_served");
        }
        for (int i = 1; i <= tables/3; i++) {
            renameFile("B" + i, "B" + i + "_served");
        }
        for (int i = 1; i <= tables/3; i++) {
            renameFile("C" + i, "C" + i + "_served");
        }*/

        onBackPressed();
    }

    public boolean fileExist(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }


    private boolean renameFile(String fileName, String newFilename) {
        File oldfile = getBaseContext().getFileStreamPath(fileName);
        File newfile = getBaseContext().getFileStreamPath(newFilename);
        boolean success = oldfile.renameTo(newfile);

        return success;
    }

    private Table retrieveTable(String tableId) {
        Table table = new Table();
        if (fileExist(tableId)) {
            try {
                FileInputStream fis = getBaseContext().openFileInput(tableId);
                ObjectInputStream is = new ObjectInputStream(fis);
                table = (Table) is.readObject();
                is.close();
                fis.close();
            } catch (IOException i) {
                System.out.println("File " + tableId + " is not accessible (because it is already open?\n");
                return null;
            } catch (ClassNotFoundException c) {
                System.out.println("Table class not found");
                c.printStackTrace();
            }
        }

        return table;
    }


    // used to get Capacity of an ArrayList
    static int getCapacity(List al) throws Exception {
        Field field = ArrayList.class.getDeclaredField("elementData");
        field.setAccessible(true);
        return ((Object[]) field.get(al)).length;
    }
    private void reset() {
        final Button reset = findViewById(R.id.reset2);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resume.super.onBackPressed();
            }
        });
    }


}