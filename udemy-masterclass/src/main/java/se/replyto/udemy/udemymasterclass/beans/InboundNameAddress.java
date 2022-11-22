package se.replyto.udemy.udemymasterclass.beans;


import lombok.Data;

@Data
public class InboundNameAddress {

    private String name;

    private String houseNumber;

    private String street;

    private String city;

    private String province;

    private String postalCode;

}