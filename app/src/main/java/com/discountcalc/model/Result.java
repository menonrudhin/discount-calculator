package com.discountcalc.model;

/**
 * This class captures price, discount %, amount, tax %, total and saved information
 */
public class Result {
    private long id;
    private String priceStr;
    private String discountStr;
    private String amountStr;
    private boolean flag;

    public Result() {
        this.id=System.currentTimeMillis();
        this.priceStr = "Price";
        this.discountStr = "Discount";
        this.amountStr = "Amount";
        this.flag = true;
    }

    /**
     * Calculate everything if any field value changes
     */
    public boolean calculate(){
        flag = true;
        try {
            double price = Double.parseDouble(this.priceStr);
            double discount = Double.parseDouble(this.discountStr);
            double amount = price - ((discount/100) * price);
            this.amountStr = String.valueOf(amount);
        } catch (NumberFormatException e){
            flag = false;
            e.printStackTrace();
            return flag;
        }

        return flag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public String getDiscountStr() {
        return discountStr;
    }

    public void setDiscountStr(String discountStr) {
        this.discountStr = discountStr;
    }

    public String getAmountStr() {
        return amountStr;
    }

    public void setAmountStr(String amountStr) {
        this.amountStr = amountStr;
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
                "id='" + id + '\'' +
                ", priceStr='" + priceStr + '\'' +
                ", discountStr='" + discountStr + '\'' +
                ", amountStr='" + amountStr + '\'' +
                '}';
    }
}