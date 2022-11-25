package se.replyto.microservices.xmluppgift.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.replyto.microservices.xmluppgift.beans.InboundCurrencyExchange;
import se.replyto.microservices.xmluppgift.beans.OutboundCurrencyExchange;

@Component
public class CurrencyExchangeRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        Logger logger = LoggerFactory.getLogger(getClass());


        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
        jacksonDataFormat.setPrettyPrint(true);

        from("file:files/xml")
                .routeId("currencyExchangeRouteId")
                .unmarshal()
                .jacksonXml(InboundCurrencyExchange.class)
                .log("Body: ${body}")
                .to("activemq:my-activemq-queue");







    }
}
