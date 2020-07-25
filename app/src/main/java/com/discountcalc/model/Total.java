package com.discountcalc.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Total {
    private List<Result> results;
    private String taxStr;
    private String totalStr;
    private String savedStr;
    private boolean flag;

    public Total() {
        this.results = new ArrayList<>();
        this.taxStr = "Tax";
        this.totalStr = "Total";
        this.savedStr = "You saved";
        this.flag = true;
    }

    /**
     * Calculate everything if any field value changes
     */
    public void calculate(){
        flag = true;
        double total = 0.0;
        double actualTotal = 0.0;
        double saved = 0.0;

        for(Result result : results){
            if(!result.calculate()) {
                this.totalStr="Error!";
                this.savedStr="Error!";
                flag = false;
                return;
            } else {
                double amount = Double.parseDouble(result.getAmountStr());
                total+=amount;
                actualTotal+=Double.parseDouble(result.getPriceStr());
                this.totalStr=String.valueOf(total);
                saved=actualTotal-total;
                this.savedStr=String.valueOf(saved);
            }
        }
    }


    public String getTaxStr() {
        return taxStr;
    }

    public void setTaxStr(String taxStr) {
        this.taxStr = taxStr;
    }

    public String getTotalStr() {
        return totalStr;
    }

    public void setTotalStr(String totalStr) {
        this.totalStr = totalStr;
    }

    public String getSavedStr() {
        return savedStr;
    }

    public void setSavedStr(String savedStr) {
        this.savedStr = savedStr;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Result{" +
                "taxStr='" + taxStr + '\'' +
                ", totalStr='" + totalStr + '\'' +
                ", savedStr='" + savedStr + '\'' +
                '}';
    }
}
