package se.replyto.microservices.errorhandling.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public  class MyPrepareProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        Logger logger = LoggerFactory.getLogger(getClass());


        Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        exchange.getIn().setHeader("FailedBecause", cause.getMessage());
        logger.info("Exception message: " + cause.getMessage());
    }
}