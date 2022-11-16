package se.replyto.microservices.camelmicroservicea.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class ActiveMqSenderRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

       /* from("timer:active-mq-timer?period=5000")
                .transform().constant("My message for Active MQ")
                .log("${body}")
                .setProperty("myProperty", constant("MyProperty"))
                .setHeader("myHeader", constant("MyHeader"))
                .to("activemq:my-activemq-queue");


        from("file:files/json")
                .log("${body}")
                .to("activemq:my-activemq-queue");*/

        from("file:files/xml")
                .log("${body}")
                .to("activemq:my-activemq-xml-queue");


    }
}
