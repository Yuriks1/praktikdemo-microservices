package se.replyto.microservices.camelmicroservicea.routes.patterns;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CurrencyExchange {
    private Long id;
    private String from;
    private String to;

    private BigDecimal conversionMultiple;

    public CurrencyExchange() {
    }


}