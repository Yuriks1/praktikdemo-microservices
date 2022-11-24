package se.replyto.microservices.errorhandling.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.replyto.microservices.errorhandling.processor.MyPrepareProcessor;


@Component
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
                .split(body().tokenize("\n", 1, true))
                .streaming()
                /*.process(exchange -> {
                    throw new RuntimeException("This is a test exception");
                })*/
                .to("file:files/output")
                .log(LoggingLevel.INFO, " Body : ${body}")
                .end();


    }
}



