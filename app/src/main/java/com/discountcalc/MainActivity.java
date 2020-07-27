package com.discountcalc;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import com.discountcalc.adapter.ResultAdapter;
import com.discountcalc.adapter.ResultAdapterTotal;
import com.discountcalc.model.Result;
import com.discountcalc.model.Total;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Context mContext=MainActivity.this;
    private static final int REQUEST = 112;

    private static RecyclerView.Adapter adapterContentMain;
    private static RecyclerView.Adapter adapterContentTotal;
    private RecyclerView.LayoutManager layoutManagerContentMain;
    private RecyclerView.LayoutManager layoutManagerContentTotal;
    private static RecyclerView recyclerViewContentMain;
    private static RecyclerView recyclerViewContentTotal;
    private static List<Result> resultsList;
    public static View.OnClickListener myOnClickListenerContentMain;
    public static View.OnClickListener myOnClickListenerContentTotal;
    private static ArrayList<Long> removedItems;
    private static Total total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<Result> results = new ArrayList<>();
        this.total = new Total();
        Result result = new Result();
        result.setPriceStr("1000");
        result.setDiscountStr("20");
        results.add(result); // initialize

        Result result2 = new Result();
        result2.setPriceStr("1000");
        result2.setDiscountStr("20");
        results.add(result2); // initialize

        Result result3 = new Result();
        result3.setPriceStr("1000");
        result3.setDiscountStr("20");
        //results.add(result3); // initialize

        Result result4 = new Result();
        result4.setPriceStr("1000");
        result4.setDiscountStr("20");
        //results.add(result4); // initialize
        this.total.setResults(results);
        this.total.calculate();

        myOnClickListenerContentMain = new MyOnClickListener(this);
        myOnClickListenerContentTotal = new MyOnClickListenerContentTotal(this);

        recyclerViewContentMain = (RecyclerView) findViewById(R.id.content_main);
        recyclerViewContentMain.setHasFixedSize(true);
        recyclerViewContentTotal = (RecyclerView) findViewById(R.id.content_total);
        recyclerViewContentTotal.setHasFixedSize(true);

        layoutManagerContentMain = new LinearLayoutManager(this);
        recyclerViewContentMain.setLayoutManager(layoutManagerContentMain);
        recyclerViewContentMain.setItemAnimator(new DefaultItemAnimator());
        layoutManagerContentTotal = new LinearLayoutManager(this);
        recyclerViewContentTotal.setLayoutManager(layoutManagerContentTotal);
        recyclerViewContentTotal.setItemAnimator(new DefaultItemAnimator());

        resultsList = new ArrayList<>();
        resultsList.addAll(results);
        removedItems = new ArrayList<Long>();

        adapterContentMain = new ResultAdapter(resultsList);
        recyclerViewContentMain.setAdapter(adapterContentMain);
        adapterContentTotal = new ResultAdapterTotal(total);
        recyclerViewContentTotal.setAdapter(adapterContentTotal);

        FloatingActionButton btnScreenShot = findViewById(R.id.btnScreenShot);
        btnScreenShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (!hasPermissions(mContext, PERMISSIONS)) {
                        ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, REQUEST );
                    } else {
                        getScreenShot(view);
                    }
                } else {
                    getScreenShot(view);
                }

            }

        });
    }

    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            Log.d("DEBUG", "**** clicked the view ****");
            int selectedItemPosition = recyclerViewContentMain.getChildAdapterPosition(v);
            long selectedItemId=resultsList.get(selectedItemPosition).getId();
            for(Result result : resultsList){
                if(result.getId() == selectedItemId){
                    EditText txtPrice = (EditText) v.findViewById(R.id.txtPrice);
                    EditText txtDiscount = (EditText) v.findViewById(R.id.txtDiscount);

                    result.setPriceStr(txtPrice.getText().toString());
                    result.setDiscountStr(txtDiscount.getText().toString());
                }
            }
            total.calculate();
            if(selectedItemPosition == (resultsList.size()-1) && resultsList.get(resultsList.size()-1).isCalculated()){
                Result result = new Result();
                resultsList.add(result); // initialize
                Log.d("DEBUG", "onClick:adding new row");
            }

            adapterContentMain.notifyDataSetChanged();
            adapterContentTotal.notifyDataSetChanged();
        }

        private void addItem(View v) {
        }


        private void removeItem(View v) {
            int selectedItemPosition = recyclerViewContentMain.getChildAdapterPosition(v);
            long selectedItemId=resultsList.get(selectedItemPosition).getId();
            removedItems.add(selectedItemId);
            resultsList.remove(selectedItemPosition);
            adapterContentMain.notifyItemRemoved(selectedItemPosition);
        }
    }

    private static class MyOnClickListenerContentTotal implements View.OnClickListener {

        private final Context context;

        private MyOnClickListenerContentTotal(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            Log.d("DEBUG", "**** clicked the total view ****");
            EditText txtYouSave = (EditText) v.findViewById(R.id.txtYouSave);
            EditText txtTax = (EditText) v.findViewById(R.id.txtTax);
            EditText txtTotal = (EditText) v.findViewById(R.id.txtTotal);

            total.setTaxStr(txtTax.getText().toString());
            total.calculate();

            txtYouSave.setText(total.getSavedStr());
            txtTotal.setText(total.getTotalStr());

            adapterContentMain.notifyDataSetChanged();
            adapterContentTotal.notifyDataSetChanged();
        }

        private void addItem(View v) {
        }


        private void removeItem(View v) {
            int selectedItemPosition = recyclerViewContentMain.getChildAdapterPosition(v);
            long selectedItemId=resultsList.get(selectedItemPosition).getId();
            removedItems.add(selectedItemId);
            resultsList.remove(selectedItemPosition);
            adapterContentMain.notifyItemRemoved(selectedItemPosition);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //getScreenShot();
                } else {
                    Toast.makeText(getApplicationContext(), "Storage permission declined", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    private void getScreenShot(View view) {
        View v = view.getRootView();
        v.setDrawingCacheEnabled(true);
        Bitmap b = v.getDrawingCache();
        String extr = Environment.getExternalStorageDirectory().toString() + "/Pictures";
        File myPath = new File(extr, getString(R.string.discount_save) + System.currentTimeMillis() + ".jpg");
        FileOutputStream fos = null;
        boolean flag = true;
        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(getContentResolver(), b, "Screen", "screen");
        } catch (FileNotFoundException e) {
            Toast toast = Toast.makeText(getApplicationContext(), "An unexpected error has occurred !", Toast.LENGTH_LONG);
            toast.show();
            flag = false;
            e.printStackTrace();
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(), "An unexpected error has occurred !", Toast.LENGTH_LONG);
            toast.show();
            flag = false;
            e.printStackTrace();

        }

        if(flag){
            Toast toast = Toast.makeText(getApplicationContext(), "Screenshot saved to " + myPath.getName(), Toast.LENGTH_LONG);
            toast.show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}