package se.replyto.udemy.udemymasterclass.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@JsonIgnoreProperties(value = { "El código postal" })

@Data
public class OutboundNameAddress {

    @JsonProperty("El nombre")
    private String name;

    @JsonProperty("La dirección")
    private String houseNumber;


    @JsonProperty("La ciudad")
    private String city;

    @JsonProperty("La provincia")
    private String province;

    @JsonProperty("El código postal")
    private String postalCode;


    public OutboundNameAddress(String name, String houseNumber, String city, String province, String postalCode) {
        this.name = name;
        this.houseNumber = houseNumber;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }
}