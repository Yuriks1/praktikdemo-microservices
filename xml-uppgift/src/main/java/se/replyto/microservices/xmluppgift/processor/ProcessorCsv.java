package se.replyto.microservices.xmluppgift.processor;

import org.apache.camel.Exchange;
import se.replyto.microservices.xmluppgift.beans.InboundCurrencyExchange;
import se.replyto.microservices.xmluppgift.beans.OutboundCsvExchange;
import se.replyto.microservices.xmluppgift.beans.OutboundCurrencyExchange;

public class ProcessorCsv implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        InboundCurrencyExchange exchangeRate = exchange.getIn().getBody(InboundCurrencyExchange.class);
        exchange.getIn().setBody(new OutboundCsvExchange(exchangeRate.getId(), exchangeRate.getFrom(),
                exchangeRate.getTo(), exchangeRate.getConversionMultiple()));
    }
}
