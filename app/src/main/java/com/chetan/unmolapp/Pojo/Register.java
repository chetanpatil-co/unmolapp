package com.chetan.unmolapp.Pojo;

/**
 * Created by Pallav on 2/6/2018.
 */

public class Register {

    private java.lang.Long id;
    String firstName;
    String lastName;
    String address;
    String mobile;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    String city;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;
    String otp;
    String couponcode;
    String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getCouponcode() {
        return couponcode;
    }

    public void setCouponcode(String couponcode) {
        this.couponcode = couponcode;
    }
}
