package se.replyto.udemy.udemymasterclass.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
//@JsonIgnoreProperties(value = { "El nombre" })

@Data
public class OutboundNameAddress {

    @JsonProperty("El nombre")
    private String name;


    @JsonProperty("La casa")
    private String address;

    public OutboundNameAddress(String name, String address) {
        this.name = name;
        this.address = address;
    }


}