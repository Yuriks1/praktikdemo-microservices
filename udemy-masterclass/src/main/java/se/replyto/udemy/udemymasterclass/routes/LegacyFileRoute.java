package se.replyto.udemy.udemymasterclass.routes;

import org.apache.camel.builder.RouteBuilder;
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

        from("file:files/input?fileName=inputFile.csv&noop=true")
                .routeId("legacyFileRouteId")
                .split(body().tokenize("\n",1,true))
                .streaming()
                .unmarshal(inboundDataFormat)
                .process(new NameAddressProcessor())
                .convertBodyTo(String.class)
                .to("file:files/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n")
                .end();
    }
}