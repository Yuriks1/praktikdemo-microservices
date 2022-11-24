package se.replyto.microservices.errorhandling.routes;

import org.apache.camel.builder.RouteBuilder;
import se.replyto.microservices.errorhandling.processor.MyPrepareProcessor;

public class DeadLetterChannel  extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        errorHandler(deadLetterChannel("jms:dead").onPrepareFailure(new MyPrepareProcessor()));

    }
}
