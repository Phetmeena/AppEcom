package com.product.appecom_test;


import java.io.Serializable;

public class Product implements Serializable {

    private String id;
    private String name;
    private Integer price;
    private String bigImageUrl;
    private String smallImageUrl;
    private Boolean sale;
    private Integer count;
    private String information;
    private String infoShoes;
    private String date;
    private String type;
    private String check_id = "";
    private String id_cart="";

    public String getType() {
        return type;
    }

    public String getId_cart() {
        return id_cart;
    }

    public void setId_cart(String id_cart) {
        this.id_cart = id_cart;
    }

    public Product(String id, String name, Integer price, String bigImageUrl, String smallImageUrl, Boolean sale, Integer count, String information, String infoShoes, String date, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.bigImageUrl = bigImageUrl;
        this.smallImageUrl = smallImageUrl;
        this.sale = sale;
        this.count = count;
        this.information = information;
        this.infoShoes = infoShoes;
        this.date = date;
        this.type = type;
    }

    public Product(String id, String name, Integer price, String bigImageUrl, String smallImageUrl, Boolean sale, Integer count, String information, String infoShoes, String date, String type, String check_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.bigImageUrl = bigImageUrl;
        this.smallImageUrl = smallImageUrl;
        this.sale = sale;
        this.count = count;
        this.information = information;
        this.infoShoes = infoShoes;
        this.date = date;
        this.type = type;
        this.check_id = check_id;
    }

    public String getCheck_id() {
        return check_id;
    }

    public void setCheck_id(String check_id) {
        this.check_id = check_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Product() {
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfoShoes() {
        return infoShoes;
    }

    public void setInfoShoes(String infoShoes) {
        this.infoShoes = infoShoes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getBigImageUrl() {
        return bigImageUrl;
    }

    public void setBigImageUrl(String bigImageUrl) {
        this.bigImageUrl = bigImageUrl;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public Boolean getSale() {
        return sale;
    }

    public void setSale(Boolean sale) {
        this.sale = sale;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
