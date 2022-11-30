package se.replyto.udemy.udemymasterclass.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.replyto.udemy.udemymasterclass.processor.NameAddressProcessor;

@Component
public class LegacyFileRoute extends RouteBuilder {

    BeanIODataFormat inboundDataFormat = new BeanIODataFormat("InboundMessageBeanIOMapping.xml","inboundMessageStream");
    Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void configure() throws Exception {

        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
        jacksonDataFormat.setPrettyPrint(true);


        from("file:files/input")
                .routeId("legacyFileRouteId")
                .log(LoggingLevel.INFO, "Original body : ${body}")
                .split(body().tokenize("\n",1,true))
                .streaming()
                .unmarshal(inboundDataFormat)
                .process(new NameAddressProcessor())
                .marshal(jacksonDataFormat)
                .log(LoggingLevel.INFO, "Transformed body : ${body}")
                .to("activemq:my-activemq-queue")
                .end();
    }
}