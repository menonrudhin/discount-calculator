package com.discountcalc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.discountcalc.MainActivity;
import com.discountcalc.R;
import com.discountcalc.model.Result;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {

    private List<Result> resultsList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        EditText txtPrice;
        EditText txtDiscount;
        EditText txtAmount;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtPrice = (EditText) itemView.findViewById(R.id.txtPrice);
            this.txtDiscount = (EditText) itemView.findViewById(R.id.txtDiscount);
            this.txtAmount = (EditText) itemView.findViewById(R.id.txtAmount);
        }
    }

    public ResultAdapter(List<Result> data) {
        this.resultsList = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_calculation, parent, false);

        view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);


        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        EditText txtPrice = holder.txtPrice;
        EditText txtDiscount = holder.txtDiscount;
        EditText txtAmount = holder.txtAmount;

        txtPrice.setText(resultsList.get(listPosition).getPriceStr());
        txtDiscount.setText(resultsList.get(listPosition).getDiscountStr());
        txtAmount.setText(resultsList.get(listPosition).getAmountStr());
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }
}
