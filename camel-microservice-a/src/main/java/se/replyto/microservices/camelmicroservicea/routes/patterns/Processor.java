package se.replyto.microservices.camelmicroservicea.routes.patterns;

import org.apache.camel.Exchange;

public class Processor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {


        CurrencyExchange exchangeRate = exchange.getIn().getBody(CurrencyExchange.class);
        exchange.getIn().setBody(new OutboundCurrencyExchange(exchangeRate.getId(),exchangeRate.getFrom()
                ,exchangeRate.getTo(),exchangeRate.getConversionMultiple()));
    }


}