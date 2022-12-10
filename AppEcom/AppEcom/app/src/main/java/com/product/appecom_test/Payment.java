package com.product.appecom_test;

public class Payment {
    private String id;
    private String name;
    private String diaChi;
    private String sdt;
    private long thanhtien;
    private String imgView;
    private String id_user;
    private String date;
    private int soluong;
    public Payment() {
    }

    public Payment(String id, String name, String diaChi, String sdt, long thanhtien, String imgView, String id_user, String date, int soluong) {
        this.id = id;
        this.name = name;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.thanhtien = thanhtien;
        this.imgView = imgView;
        this.id_user = id_user;
        this.date = date;
        this.soluong = soluong;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public long getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(long thanhtien) {
        this.thanhtien = thanhtien;
    }

    public String getImgView() {
        return imgView;
    }

    public void setImgView(String imgView) {
        this.imgView = imgView;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
