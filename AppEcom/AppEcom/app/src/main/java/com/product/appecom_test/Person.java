package com.product.appecom_test;

public class Person {
    private  String id;
    private String userName;

    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    private Gender gender;

    private String address;

    private String birthDay;

    private String avatarUrl;
    private String imageCoverUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Person() {
    }

    public Person(String id, String userName, String password, String firstName, String lastName,
                  String phone, Gender gender, String address, String birthDay, String avatarUrl, String imageCoverUrl, String userType) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.gender = gender;
        this.address = address;
        this.birthDay = birthDay;
        this.avatarUrl = avatarUrl;
        this.imageCoverUrl = imageCoverUrl;
        this.userType = userType;
    }

    private String userType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getImageCoverUrl() {
        return imageCoverUrl;
    }

    public void setImageCoverUrl(String imageCoverUrl) {
        this.imageCoverUrl = imageCoverUrl;
    }
}
