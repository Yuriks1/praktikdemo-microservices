package se.replyto.microservices.xmluppgift.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@JsonIgnoreProperties(value = {"ID"})

@Data
public class OutboundCurrencyExchange {

    @JsonProperty("ID")
    Long id;

    @JsonProperty("source")
    String from;

    @JsonProperty("dest")
    String to;

    @JsonProperty("convRate")
    Double conversionMultiple;


}
