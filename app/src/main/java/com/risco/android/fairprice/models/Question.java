package com.risco.android.fairprice.models;

/**
 * Created by Albert Risco on 22/01/2018.
 */

public class Question {
    private String product, url;
    private Long real_price, number;

    public Question(String product, String url, Long real_price, Long number) {
        this.product = product;
        this.url = url;
        this.real_price = real_price;
        this.number = number;
    }

    public Question(){}

    public String getProduct() {
        return product;
    }

    public String getUrl() {
        return url;
    }

    public Long getReal_price() {
        return real_price;
    }

    public Long getNumber() {
        return number;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setReal_price(Long real_price) {
        this.real_price = real_price;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Question{" +
                "product='" + product + '\'' +
                ", url='" + url + '\'' +
                ", real_price=" + real_price +
                ", number=" + number +
                '}';
    }
}
