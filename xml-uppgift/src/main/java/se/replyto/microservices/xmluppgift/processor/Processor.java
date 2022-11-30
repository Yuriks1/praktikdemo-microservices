package se.replyto.microservices.xmluppgift.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import se.replyto.microservices.xmluppgift.beans.InboundCurrencyExchange;
import se.replyto.microservices.xmluppgift.beans.OutboundCurrencyExchange;

import javax.sound.sampled.Line;
import java.util.ArrayList;

public class Processor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        InboundCurrencyExchange exchangeRate = exchange.getIn().getBody(InboundCurrencyExchange.class);
        exchange.getIn().setBody(new OutboundCurrencyExchange(exchangeRate.getId(), exchangeRate.getFrom(),
                exchangeRate.getTo(), exchangeRate.getConversionMultiple()));


    }
}