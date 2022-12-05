package se.replyto.microservices.xmluppgift.processor;

import org.apache.camel.Exchange;
import se.replyto.microservices.xmluppgift.beans.InboundCurrencyExchange;
import se.replyto.microservices.xmluppgift.beans.OutboundCsvExchange;
import se.replyto.microservices.xmluppgift.beans.OutboundCurrencyExchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Processor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        InboundCurrencyExchange exchangeRate = exchange.getIn().getBody(InboundCurrencyExchange.class);
      //  exchange.getIn().setBody(new OutboundCurrencyExchange(exchangeRate.getId(), exchangeRate.getFrom(),
       //         exchangeRate.getTo(), exchangeRate.getConversionMultiple()));
/*
        List<OutboundCurrencyExchange> outBody = new ArrayList<>();
        outBody.add(new OutboundCurrencyExchange(exchangeRate.getId(), exchangeRate.getFrom(),
                exchangeRate.getTo(), exchangeRate.getConversionMultiple()));
        exchange.getIn().setBody(outBody);*/

        List<Map<String, Object>> outBody = new ArrayList<>();
        OutboundCsvExchange outboundCsvExchange1 = new OutboundCsvExchange(exchangeRate.getId(), exchangeRate.getFrom(),
                exchangeRate.getTo(), exchangeRate.getConversionMultiple());
        OutboundCsvExchange outboundCsvExchange2 = new OutboundCsvExchange(exchangeRate.getId(), exchangeRate.getFrom(),
                exchangeRate.getTo(), exchangeRate.getConversionMultiple());
        OutboundCsvExchange outboundCsvExchange3 = new OutboundCsvExchange(exchangeRate.getId(), exchangeRate.getFrom(),
                exchangeRate.getTo(), exchangeRate.getConversionMultiple());
        Map<String, Object> line1 = new HashMap<>();
        line1.put("se.replyto.microservices.xmluppgift.beans.OutboundCurrencyExchange", outboundCsvExchange1);
        Map<String, Object> line2 = new HashMap<>();
        line1.put("se.replyto.microservices.xmluppgift.beans.OutboundCurrencyExchange", outboundCsvExchange2);
        Map<String, Object> line3 = new HashMap<>();
        line1.put("se.replyto.microservices.xmluppgift.beans.OutboundCurrencyExchange", outboundCsvExchange3);
        outBody.add(line1);
        outBody.add(line2);
        outBody.add(line3);
        exchange.getIn().setBody(outBody);










    }
}
