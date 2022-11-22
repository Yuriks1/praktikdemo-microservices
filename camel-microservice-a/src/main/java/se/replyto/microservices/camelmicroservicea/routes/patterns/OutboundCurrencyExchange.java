package se.replyto.microservices.camelmicroservicea.routes.patterns;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@JsonIgnoreProperties(value = { "ID" })

@Data
public class OutboundCurrencyExchange {
    @JsonProperty("ID")
    private Long id;

    @JsonProperty("source")
    private String from;

    @JsonProperty("dest")

    private String to;

    @JsonProperty("convRate")
    private BigDecimal conversionMultiple;

    public OutboundCurrencyExchange(Long id, String from, String to, BigDecimal conversionMultiple) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.conversionMultiple = conversionMultiple;
    }
}
