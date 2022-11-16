package se.replyto.microservices.camelmicroservicea.routes.b;

import org.apache.camel.builder.RouteBuilder;

//@Component
public class MyFileRouter extends RouteBuilder {


    @Override
    public void configure() throws Exception {


        from("file:files/input")

                .choice() //Content Based Routing
                .when(simple("${file:ext} == 'xml'"))
                .log("XML FILE")
                .pipeline()

                .log("Not an XML FILE BUT contains USD")
                .end()
                .log("Not an XML FILE")
                .end()
                .to("file:files/output");

    }
}

