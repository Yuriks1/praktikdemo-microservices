package se.replyto.microservices.xmluppgift.processor;

import org.apache.camel.Exchange;
import org.apache.logging.log4j.Level;
import se.replyto.microservices.xmluppgift.beans.InboundCurrencyExchange;
import se.replyto.microservices.xmluppgift.beans.OutboundCsvExchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessorCsv implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {


        List<Map<String, String>> body = new ArrayList<>();

        HashMap<String, String> row1 = new HashMap<>();
        row1.put("FromCurrency","cc1" );
        row1.put("ToCurrency", "CC2");
        row1.put("CurrencyRate", "1.23");

        HashMap<String, String> row2 = new HashMap<>();
        row2.put("FromCurrency", "CC3");
        row2.put("ToCurrency", "CC4");
        row2.put("CurrencyRate", "3.21");

        body.add(row1);
        body.add(row2);

        exchange.getMessage().setBody(body);





    }
}
