package se.replyto.microservices.xmluppgift.beans;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ",", crlf = "UNIX")
public class OutboundCsvExchange {

    @DataField(pos = 1, columnName = "Id")
    Long id;

    @DataField(pos = 2, columnName = "source")
    String from;

    @DataField(pos = 3, columnName = "dest")
    String to;

    @DataField(pos = 4, columnName = "convRate")
    int conversionMultiple;

    public OutboundCsvExchange() {
    }

    public OutboundCsvExchange(Long id, String from, String to, int conversionMultiple) {
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

    public int getConversionMultiple() {
        return conversionMultiple;
    }

    public void setConversionMultiple(int conversionMultiple) {
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