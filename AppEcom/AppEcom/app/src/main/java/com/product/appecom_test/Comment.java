package com.product.appecom_test;

public class Comment {
    private String id;
    private String comment;
    private String product;
    private String date;
    private String name;

    public Comment() {
    }

    public Comment(String id, String comment, String product, String date, String name) {
        this.id = id;
        this.comment = comment;
        this.product = product;
        this.date = date;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String Product) {
        this.product = Product;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
