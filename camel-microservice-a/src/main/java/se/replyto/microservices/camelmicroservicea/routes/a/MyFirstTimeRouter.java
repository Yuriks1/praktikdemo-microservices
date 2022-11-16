package se.replyto.microservices.camelmicroservicea.routes.a;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.logging.Logger;

//@Component
public class MyFirstTimeRouter extends RouteBuilder {

    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;


    @Override
    public void configure() throws Exception {


        from("timer:first-timer")
                .log("${body}")
                .transform().constant("My Constant Message")
                .log("${body}")
                //.transform().constant("Time is " + LocalDateTime.now())
                //.bean("getCurrentTimeBean")
                .bean(getCurrentTimeBean,"process")
                .log("${body}")
                .log("${headers}")
                .to("log:first-timer")
                .log("${body}")
        ;
    }
}

    //@Component
class GetCurrentTimeBean {

    public String getCurrentTime() {
        return "Time now is " + LocalDateTime.now();
    }
    public void process(Exchange exchange) throws Exception {
        exchange.getMessage().setBody("Time now is " + LocalDateTime.now());
        exchange.getMessage().setHeader("MyHeader", "MyHeaderValue");
    }
}

