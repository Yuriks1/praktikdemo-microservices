package se.replyto.microservices.camelmicroserviceb.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.replyto.microservices.camelmicroserviceb.CurrencyExchange;

import java.math.BigDecimal;

@Component
public class ActiveMqReceiverRouter extends RouteBuilder {

    @Autowired
    private MyCurrencyExchangeProcessor myCurrencyExchangeProcessor;
    @Autowired
    private MyCurrencyExchangeProcessorTransformer myCurrencyExchangeProcessorTransformer;

    @Override
    public void configure() throws Exception {


        /*from("activemq:my-activemq-queue")
                .unmarshal().json(JsonLibrary.Jackson,CurrencyExchange.class)
                .bean(myCurrencyExchangeProcessor)
                .bean(myCurrencyExchangeProcessorTransformer)
                .to("log:received-message-from-active-mq");*/


//        from("activemq:my-activemq-xml-queue")
//                .unmarshal()
//                .jacksonXml(CurrencyExchange.class)
//                .bean(myCurrencyExchangeProcessor)
//                .bean(myCurrencyExchangeProcessorTransformer)
//                .to("log:received-message-from-active-xml-mq");

        from("activemq:split-queue")

                .to("log:received-message-from-active-mq");

    }

}

@Component
class MyCurrencyExchangeProcessor {
    Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeProcessor.class);

    public void processMessage(CurrencyExchange currencyExchange) {
        logger.info("Do some processing with currencyExchange.getConversionMultiple() value which is   {}",
                currencyExchange.getConversionMultiple());
    }
}

@Component
class MyCurrencyExchangeProcessorTransformer {
    Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeProcessor.class);

    public CurrencyExchange processMessage(CurrencyExchange currencyExchange) {


        currencyExchange.setConversionMultiple(currencyExchange.getConversionMultiple()
                .multiply(BigDecimal.TEN));

        logger.info("Do some processing with currencyExchange.getConversionMultiple() value which is   {}",
                currencyExchange.getConversionMultiple());
        return currencyExchange;
    }
}


