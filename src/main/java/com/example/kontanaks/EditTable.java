package com.example.kontanaks;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kontanaks.etc.Product;
import com.example.kontanaks.etc.Table;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditTable extends AppCompatActivity {
    String tableId;
    boolean numbered = true;
    private Table table = null;
    private int currentItem = 0;
    Product lastProductAdded = null;
    List<String> productNames = null;
    int currentlySelectedProduct = 0;


    int editId = R.id.editTextTextMultiLine;
    int editId2 = R.id.editTextTextMultiLine2;

    @Override
    public void onPause(){
        super.onPause();
        productNames = table.getProductNames();
        lastProductAdded = null;
        EditTable.super.onBackPressed();
    }

    public boolean fileExists(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tableId = getIntent().getStringExtra("tableId");
        if (fileExists(tableId + "_served")) {
            tableId = tableId + "_served";
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_table);

        ScrollView sv = findViewById(R.id.products);
        ViewGroup.LayoutParams layoutParams = sv.getLayoutParams();
        DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        height = height-1200;
        if (height < 600)
            layoutParams.height = height;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.rgb(61, 133, 198));
        }


        final EditText edit = findViewById(editId);
        final EditText edit2 = findViewById(editId2);
        edit.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_FLAG_MULTI_LINE |
                InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        edit2.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_FLAG_MULTI_LINE |
                InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        edit.setFocusable(false);
        edit2.setFocusable(false);


        // Way of shrinking (by height) the size of the edit element (in our case the edittext, it can be used in any visible element in the XML)
        layoutParams = edit.getLayoutParams();
        displayMetrics = getBaseContext().getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        height = height-1200;
        if (height < 600)
            layoutParams.height = height;

        reset();


        restoreTable();
        productNames = table.getProductNames();

        edit.setText(table.getSerializedProducts());
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText edit2 = findViewById(editId2);
                if (table.map.size() != 0) {
                    if (currentlySelectedProduct < table.map.size() - 1) {
                        edit2.setText(productNames.get(currentlySelectedProduct));
                        lastProductAdded = table.getProductByName(productNames.get(currentlySelectedProduct));
                        currentlySelectedProduct++;
                    } else {
                        edit2.setText(productNames.get(currentlySelectedProduct));
                        lastProductAdded = table.getProductByName(productNames.get(currentlySelectedProduct));
                        currentlySelectedProduct = 0;
                    }
                }
                else
                    Toast.makeText(edit2.getContext(), "Παρακαλώ εισάγετε καταχώρηση πρωτού προσπαθήσετε να επιλέξετε.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void addDetail(String detail, double price) {
        if (lastProductAdded != null) {
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_add);
            mp.start();
            Product p = table.getProductByName(lastProductAdded.getName());
            Product p_temp = new Product(p);
            if (p_temp.addDetail(detail)) {
                p_temp.setPrice(p_temp.getPrice() + price);
                table.removeProduct(p);
                addProduct(p_temp);
                lastProductAdded = p_temp;
                productNames = table.getProductNames();
            } else
                Toast.makeText(this, "Η καταχώρηση αυτή έχει ήδη τη λεπτομέρεια '" + detail + "'.", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Παρακαλώ επιλέξτε καταχώρηση, κάνοντας tap στη λίστα καταχωρήσεων ή εισάγετε απο τη λιστα προϊόντων.", Toast.LENGTH_SHORT).show();
    }

    public void removeOne(View v) {

        if (lastProductAdded != null) {
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_remove);
            mp.start();
            table.removeProductByName(lastProductAdded.getName());
            if (table.getProductByName(lastProductAdded.getName()) == null)
                lastProductAdded = null;
            productNames = table.getProductNames();
            persistTable();
            return;
        }
        else
            Toast.makeText(this, "Παρακαλώ επιλέξτε καταχώρηση, κάνοντας tap στη λίστα καταχωρήσεων ή εισάγετε απο τη λιστα προϊόντων.", Toast.LENGTH_SHORT).show();
    }
    public void addOne(View v) {
        if (lastProductAdded != null) {
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_add);
            mp.start();
            addProduct(lastProductAdded);
            return;
        }
        else
            Toast.makeText(this, "Παρακαλώ επιλέξτε καταχώρηση, κάνοντας tap στη λίστα καταχωρήσεων ή εισάγετε απο τη λιστα προϊόντων.", Toast.LENGTH_SHORT).show();
        return;
    }

    public void espressoPopup(View v) {
        final String toFix = "espresso";

        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.espresso, popup.getMenu());

        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
            }
        });

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                int capacity = 0;
                try {
                    capacity = getCapacity(MainActivity.productList.espresso);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < capacity; i++) {
                    if (item.getItemId() == getMenuItemId(toFix, i)) {
                        Product toAdd = new Product(MainActivity.productList.espresso.get(i));
                        toAdd.resetDetails();
                        addProduct(toAdd);
                    }
                };
                return true;
            }
        });
        popup.show();
    }
    public void beersPopup(View v) {
        final String toFix = "beers";

        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.beers, popup.getMenu());

        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
            }
        });

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                int capacity = 0;
                try {
                    capacity = getCapacity(MainActivity.productList.beers);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < capacity; i++) {
                    if (item.getItemId() == getMenuItemId(toFix, i)) {
                        Product toAdd = new Product(MainActivity.productList.beers.get(i));
                        toAdd.resetDetails();
                        addProduct(toAdd);
                    }
                };
                return true;
            }
        });
        popup.show();
    }
    public void nescafePopup(View v) {
        final String toFix = "nescafe";

        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.nescafe, popup.getMenu());

        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
            }
        });

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                int capacity = 0;
                try {
                    capacity = getCapacity(MainActivity.productList.nescafe);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < capacity; i++) {
                    if (item.getItemId() == getMenuItemId(toFix, i)) {
                        Product toAdd = new Product(MainActivity.productList.nescafe.get(i));
                        toAdd.resetDetails();
                        addProduct(toAdd);
                    }
                };
                return true;
            }
        });
        popup.show();
    }
    public void teaPopup(View v) {
        final String toFix = "tea";

        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.tea, popup.getMenu());

        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
            }
        });

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                int capacity = 0;
                try {
                    capacity = getCapacity(MainActivity.productList.tea);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < capacity; i++) {
                    if (item.getItemId() == getMenuItemId(toFix, i)) {
                        Product toAdd = new Product(MainActivity.productList.tea.get(i));
                        toAdd.resetDetails();
                        addProduct(toAdd);
                    }
                };
                return true;
            }
        });
        popup.show();
    }
    public void ximoiPopup(View v) {
        final String toFix = "ximoi";

        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.ximoi, popup.getMenu());

        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
            }
        });

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                int capacity = 0;
                try {
                    capacity = getCapacity(MainActivity.productList.ximoi);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < capacity; i++) {
                    if (item.getItemId() == getMenuItemId(toFix, i)) {
                        Product toAdd = new Product(MainActivity.productList.ximoi.get(i));
                        toAdd.resetDetails();
                        addProduct(toAdd);
                    }
                };
                return true;
            }
        });
        popup.show();
    }
    public void anthrakouxaPopup(View v) {
        final String toFix = "anthrakouxa";

        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.anthrakouxa, popup.getMenu());

        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
            }
        });

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                int capacity = 0;
                try {
                    capacity = getCapacity(MainActivity.productList.anthrakouxa);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < capacity; i++) {
                    if (item.getItemId() == getMenuItemId(toFix, i)) {
                        Product toAdd = new Product(MainActivity.productList.anthrakouxa.get(i));
                        toAdd.resetDetails();
                        addProduct(toAdd);
                    }
                };
                return true;
            }
        });
        popup.show();
    }
    public void toastPopup(View v) {
        final String toFix = "toast";

        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.toast, popup.getMenu());

        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
            }
        });

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                int capacity = 0;
                try {
                    capacity = getCapacity(MainActivity.productList.toast);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < capacity; i++) {
                    if (item.getItemId() == getMenuItemId(toFix, i)) {
                        Product toAdd = new Product(MainActivity.productList.toast.get(i));
                        toAdd.resetDetails();
                        addProduct(toAdd);
                    }
                };
                return true;
            }
        });
        popup.show();
    }
    public void fagitoPopup(View v) {
        final String toFix = "fagito";

        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.fagito, popup.getMenu());

        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
            }
        });

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                int capacity = 0;
                try {
                    capacity = getCapacity(MainActivity.productList.fagito);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < capacity; i++) {
                    if (item.getItemId() == getMenuItemId(toFix, i)) {
                        Product toAdd = new Product(MainActivity.productList.fagito.get(i));
                        toAdd.resetDetails();
                        addProduct(toAdd);
                    }
                };
                return true;
            }
        });
        popup.show();
    }
    public void neroPopup(View v) {
        final String toFix = "nero";

        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.nero, popup.getMenu());

        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
            }
        });

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                int capacity = 0;
                try {
                    capacity = getCapacity(MainActivity.productList.nero);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < capacity; i++) {
                    if (item.getItemId() == getMenuItemId(toFix, i)) {
                        Product toAdd = new Product(MainActivity.productList.nero.get(i));
                        toAdd.resetDetails();
                        addProduct(toAdd);
                    }
                };
                return true;
            }
        });
        popup.show();
    }

    public void etcPopup(View v) {
        final String toFix = "etc";

        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.etc, popup.getMenu());

        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
            }
        });

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                int capacity = 0;
                try {
                    capacity = getCapacity(MainActivity.productList.etc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < capacity; i++) {
                    if (item.getItemId() == getMenuItemId(toFix, i)) {
                        Product detail = MainActivity.productList.etc.get(i);
                        addDetail(detail.getName(), detail.getPrice());
                    }
                };
                return true;
            }
        });
        popup.show();
    }


    private void addProduct(Product p)
    {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_add);
        mp.start();
        lastProductAdded = p;
        table.addProduct(p);
        productNames = table.getProductNames();
        persistTable();
    }

    private void refreshPrice() {
        final Button reset = findViewById(R.id.reset2);
        if (table.getPrice() > 0)
            reset.setText(" (" + table.getPrice() + " €) ΕΞΟΦΛΙΣΗ ");
        else
            reset.setText(" ΠΙΣΩ ");
    }

    public int getMenuItemId(String prefix, int i) {

        String viewholderName = prefix + i;
        int id_2 = 0;
        try {
            id_2 = R.id.class.getField(viewholderName).getInt(0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return id_2;
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (table.getPrice() == 0) {
            deleteFile(tableId);
            return;
        }
        EditTable.super.finish();
    }

    public static boolean hasNavigationBar(Activity activity) {
        Rect rectangle = new Rect();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels != (rectangle.top + rectangle.height());
    }


    private void reset() {
        final Button reset = findViewById(R.id.reset2);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (table.getPrice() == 0) {
                    EditTable.super.finish();
                    deleteFile(tableId);
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(reset.getContext());

                builder.setTitle("Πληρωμή τραπεζιού");
                builder.setMessage("Είστε σίγουρος οτι πληρωθήκατε " + table.getPrice() + " ευρώ?");

                builder.setPositiveButton("Ναι", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                        deleteFile(tableId);
                        dialog.dismiss();
                        EditTable.super.onBackPressed();
                    }
                });

                builder.setNegativeButton("Όχι", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    // TODO: Fix it, it is working with the workaround of the "setText" that surround it.
    private void persistTable() {

        final EditText edit = findViewById(editId);
        final EditText edit2 = findViewById(editId2);
        String fileName = tableId;
        try {
            FileOutputStream fos = getBaseContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(table);
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        edit.setText(table.getSerializedProducts());
        if (lastProductAdded != null)
            edit2.setText(lastProductAdded.getName());
        else
            edit2.setText("");

        productNames = table.getProductNames();
        currentlySelectedProduct=0;

        refreshPrice();

        if (table.map.size() == 0) {
            deleteFile(tableId);
            return;
        }
    }

    private void restoreTable() {
        Table table = new Table();
        try {
            FileInputStream fis = getBaseContext().openFileInput(tableId);
            ObjectInputStream is = new ObjectInputStream(fis);
            table = (Table) is.readObject();
            is.close();
            fis.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
        }

        productNames = table.getProductNames();
        this.table = table;

        productNames = this.table.getProductNames();
        currentlySelectedProduct=0;

        refreshPrice();
    }

    private int count(Product p, String wholeText)
    {
        Pattern pattern = Pattern.compile(p.getName());
        Matcher matcher = pattern.matcher(wholeText);
        int c = 0;
        while (matcher.find()) {
            c++;
        }
        return c;
    }

    // used to get Capacity of an ArrayList
    static int getCapacity(List al) throws Exception {
        Field field = ArrayList.class.getDeclaredField("elementData");
        field.setAccessible(true);
        return ((Object[]) field.get(al)).length;
    }
}