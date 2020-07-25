package com.discountcalc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.discountcalc.R;
import com.discountcalc.model.Result;
import java.util.List;

public class ResultAdapter extends ArrayAdapter<Result> {

    public ResultAdapter(Context context, List<Result> users) {
        super(context, 0, users);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Result result = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_calculation, parent, false);

        }

        // Lookup view for data population
        TextView txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
        TextView txtDiscount = (TextView) convertView.findViewById(R.id.txtDiscount);
        TextView txtAmount = (TextView) convertView.findViewById(R.id.txtAmount);

        // Populate the data into the template view using the data object
        txtPrice.setText(result.getPriceStr());
        txtDiscount.setText(result.getDiscountStr());
        txtAmount.setText(result.getAmountStr());

        // Return the completed view to render on screen
        return convertView;
    }

}
