package se.replyto.microservices.xmluppgift.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CurrencyExchangeRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        BeanIODataFormat inboundDataFormat = new BeanIODataFormat("files/xml/InboundMessageBeanIOMapping.xml","inboundCurrencyStream");


        Logger logger = LoggerFactory.getLogger(getClass());


        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
        jacksonDataFormat.setPrettyPrint(true);

        from("file:files/xml")
                .routeId("currencyExchangeRouteId")
                .log(LoggingLevel.INFO, "Original body : ${body}")
                .split(body().tokenize("\n",1,true))
                .streaming()
                .unmarshal(inboundDataFormat)
//                .unmarshal()
//                .jacksonXml(InboundCurrencyExchange.class)
                .log("Body: ${body}")
                .to("activemq:my-activemq-queue");







    }
}
