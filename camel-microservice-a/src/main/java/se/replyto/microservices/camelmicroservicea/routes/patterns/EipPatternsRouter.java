package se.replyto.microservices.camelmicroservicea.routes.patterns;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class EipPatternsRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        /*
        pipeline
        Content Based Router = choice()
        Multicast

                from("timer:multicast?period=10000")
                        .multicast()
                        .to("log:example1", "log:example2", "log:example3");
        */

        from("file:files/csv")
                .unmarshal().csv()
                .split(body())
                .to("activemq:split-queue");


    }
}
