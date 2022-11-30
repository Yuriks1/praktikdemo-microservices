package se.replyto.microservices.xmluppgift.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ",",generateHeaderColumns = true )
public class OutboundCsvExchange {

    @DataField(pos = 1)
    Long id;

    @DataField(pos = 2)
    String from;

    @DataField(pos = 3)
    String to;

    @DataField(pos = 4)
    Double conversionMultiple;

    public OutboundCsvExchange() {
    }

    public OutboundCsvExchange(Long id, String from, String to, Double conversionMultiple) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.conversionMultiple = conversionMultiple;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getConversionMultiple() {
        return conversionMultiple;
    }

    public void setConversionMultiple(Double conversionMultiple) {
        this.conversionMultiple = conversionMultiple;
    }

    @Override
    public String toString() {
        return "OutboundCsvExchange{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", conversionMultiple=" + conversionMultiple +
                '}';
    }
}