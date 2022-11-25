package se.replyto.microservices.xmluppgift.beans;


import lombok.Data;

@Data
public class InboundCurrencyExchange {

    Long id;
    String from;
    String to;
    Double conversionMultiple;
}
