package com.discountcalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.math.BigDecimal;

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
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toast = Toast.makeText(getContext(), "Please enter a valid value", Toast.LENGTH_SHORT);

        final EditText txtMrp = (EditText) view.findViewById(R.id.actual_price);

        final EditText txtDiscount = (EditText) view.findViewById(R.id.discount_perc);

        final EditText txtResult = (EditText) view.findViewById(R.id.discount_amount);

        txtMrp.setSelectAllOnFocus(true);

        txtDiscount.setSelectAllOnFocus(true);

        txtMrp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (txtMrp.getText().toString().length() == 0) {
                        txtMrp.setText(R.string.actual_price);

                        isMrpCleaned = false;
                    }
                } else {
                    if (!isMrpCleaned && txtMrp.getText().toString().equals(R.string.actual_price)) {
                        txtMrp.setText("");

                        isMrpCleaned = true;
                    }
                }
            }
        });

        txtDiscount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (txtDiscount.getText().toString().length() == 0) {
                        txtDiscount.setText(R.string.discount_perc);

                        isDiscountCleaned = false;
                    }
                } else {
                    if (!isDiscountCleaned && txtDiscount.getText().toString().equals(R.string.discount_perc)) {
                        txtDiscount.setText("");

                        isDiscountCleaned = true;
                    }
                }
            }
        });

        view.findViewById(R.id.btnCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast toast = Toast.makeText(getContext(), "Clicked", Toast.LENGTH_LONG);
                onClickBtnCalculate(view, txtMrp, txtDiscount, txtResult);
                //toast.show();
            }
        });
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