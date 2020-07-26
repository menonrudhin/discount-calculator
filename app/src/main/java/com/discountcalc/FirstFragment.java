package com.discountcalc;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.discountcalc.adapter.ResultAdapter;
import com.discountcalc.model.Result;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {
    private boolean responseCode = true;

    private Toast toast;



    protected boolean isMrpCleaned = false;

    protected boolean isDiscountCleaned = false;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculation, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        toast = Toast.makeText(getContext(), "Please enter a valid value", Toast.LENGTH_SHORT);
    }

    public boolean onClickBtnCalculate(View v, EditText txtMrp, EditText txtDiscount, EditText txtResult) {
        responseCode = true;

        //EditText txtMrp = (EditText) v.findViewById(R.id.actual_price);
        //EditText txtDiscount = (EditText) v.findViewById(R.id.discount_perc);

        //EditText txtResult = (EditText) v.findViewById(R.id.discount_amount);

        if((txtMrp.getText().toString() == null) || (txtDiscount.getText().toString() == null))
        {
            responseCode = false;

            txtResult.setText("");

            toast.show();

            return responseCode;
        }

        double mrp = 0;

        double discount = 0;

        try {
            mrp = Double.parseDouble(txtMrp.getText().toString());
            discount = Double.parseDouble(txtDiscount.getText().toString());
        } catch (NumberFormatException e) {
            responseCode = false;

            txtResult.setText("");

            toast.show();

            return responseCode;
        }

        if((discount > 100) || (mrp <= 0))
        {
            responseCode = false;

            txtResult.setText("");

            toast.show();

            return responseCode;
        }

        BigDecimal resultBD = new BigDecimal(mrp - (mrp * discount * 0.01));

        resultBD.setScale(2, BigDecimal.ROUND_HALF_DOWN);

        String result = resultBD.toString();

        if((result.indexOf('.') != -1) && (result.substring(result.indexOf('.') + 1).length() > 2))
        {
            result = result.substring(0, result.indexOf('.') + 3);
        }

        txtResult.setText(result);

        return responseCode;
    }
}