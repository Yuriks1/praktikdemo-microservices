package se.replyto.udemy.udemymasterclass.beans;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


public class InboundNameAddress {

    private String name;

    private String houseNumber;

    private String city;

    private String province;

    private String postalCode;

    public String getName() {
        return name;
    }

    public InboundNameAddress() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public InboundNameAddress(String name, String houseNumber, String city, String province, String postalCode) {
        this.name = name;
        this.houseNumber = houseNumber;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;


    }
}