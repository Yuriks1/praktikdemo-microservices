package se.replyto.udemy.udemymasterclass.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
import se.replyto.udemy.udemymasterclass.beans.InboundNameAddress;
import se.replyto.udemy.udemymasterclass.processor.NameAddressProcessor;



//@Component
public class NewRestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {


    }
}