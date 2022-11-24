package se.replyto.microservices.errorhandling.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public  class MyPrepareProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        exchange.getIn().setHeader("FailedBecause", cause.getMessage());
    }
}