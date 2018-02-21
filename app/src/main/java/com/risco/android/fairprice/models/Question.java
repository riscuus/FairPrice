package com.risco.android.fairprice.models;

/**
 * Created by Albert Risco on 22/01/2018.
 */

public class Question {
    private String product, url, image;
    private Long realprice, number;

    public Question(String product, String url, Long realprice, Long number, String image) {
        this.product = product;
        this.url = url;
        this.realprice = realprice;
        this.number = number;
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Question{" +
                "product='" + product + '\'' +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                ", realprice=" + realprice +
                ", number=" + number +
                '}';
    }

    public Question(){}

    public String getProduct() {
        return product;
    }

    public String getUrl() {
        return url;
    }

    public Long getRealprice() {
        return realprice;
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

    public void setRealprice(Long realprice) {
        this.realprice = realprice;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

}
