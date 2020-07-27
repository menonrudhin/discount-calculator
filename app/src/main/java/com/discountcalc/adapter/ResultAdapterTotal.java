package com.discountcalc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.discountcalc.MainActivity;
import com.discountcalc.R;
import com.discountcalc.model.Result;
import com.discountcalc.model.Total;

import java.util.List;

public class ResultAdapterTotal extends RecyclerView.Adapter<ResultAdapterTotal.MyViewHolder> {

    private Total total;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        EditText txtYouSave;
        EditText txtTax;
        EditText txtTotal;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtYouSave = (EditText) itemView.findViewById(R.id.txtYouSave);
            this.txtTax = (EditText) itemView.findViewById(R.id.txtTax);
            this.txtTotal = (EditText) itemView.findViewById(R.id.txtTotal);
        }
    }

    public ResultAdapterTotal(Total total) {
        this.total = total;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_total, parent, false);

        view.setOnClickListener(MainActivity.myOnClickListenerContentTotal);

        MyViewHolder myViewHolder = new MyViewHolder(view);


        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        EditText txtYouSave = (EditText) holder.txtYouSave;
        EditText txtTax = (EditText) holder.txtTax;
        EditText txtTotal = (EditText) holder.txtTotal;

        total.setTaxStr(txtTax.getText().toString());
        total.calculate();

        txtYouSave.setText(total.getSavedStr());
        txtTotal.setText(total.getTotalStr());
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
