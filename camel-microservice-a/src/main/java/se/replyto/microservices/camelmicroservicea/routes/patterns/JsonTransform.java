package se.replyto.microservices.camelmicroservicea.routes.patterns;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

@JsonPropertyOrder({
        "from",
        "to",
        "conversionMultiple"
})
public abstract class JsonTransform {

    @JsonProperty("from")
    private String source;

    @JsonProperty("to")
    private int dest;

    @JsonProperty("conversionMultiple")
    private BigDecimal convRate;

    private Long id;

}