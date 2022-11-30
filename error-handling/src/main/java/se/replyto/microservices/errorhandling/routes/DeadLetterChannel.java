package se.replyto.microservices.errorhandling.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.replyto.microservices.errorhandling.processor.MyPrepareProcessor;


//@Component
public class DeadLetterChannel extends RouteBuilder {
    @Override
    public void configure() throws Exception {


        BeanIODataFormat inboundDataFormat = new BeanIODataFormat("InboundMessageBeanIOMapping.xml", "inboundMessageStream");
        Logger logger = LoggerFactory.getLogger(getClass());


        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
        jacksonDataFormat.setPrettyPrint(true);




        from("file:files/input")
                .routeId("errorRouteId")
                .errorHandler(deadLetterChannel("jms:deadMessages").onPrepareFailure(new MyPrepareProcessor()))
                .log(LoggingLevel.INFO, "Original body : ${body}")

                //.setHeader(Exchange.FILE_NAME, constant("example.txt"))

//                //.split(body().tokenize("\n", 1, true)).stopOnException()
//                .streaming()
//                .convertBodyTo(String.class).process(exchange -> {
//                    System.out.println("Exchange id:"+ exchange.getExchangeId());
//                    System.out.println("Headers :"+ exchange.getMessage().getHeaders());
//                    System.out.println(exchange.getIn().getBody());
//                    exchange.getMessage().setHeader("CamelFileName",exchange.getMessage().getHeader("CamelFileName") + ".out");
//                })
//                /*.process(exchange -> {
//                    throw new RuntimeException("This is a test exception");
//                })*/

                //.split(xpath("//catalog/book[@id='id100']/author/text()"))  // Author name
                //.split(xpath("//catalog/book/author[@age<40]/text()"))  //  just authors over 40 years old
                //.split(xpath("//catalog/book[@id='id100']/author/text()")) // Authors whose id matches with ‘id100’

                .to("log:output");
//                .to("file:files/output")
//                .log(LoggingLevel.INFO, " Body : ${body}")
//                //.log("File Name: ${header.CamelFileName}, Body:${body} ")
//                .end();


    }
}


