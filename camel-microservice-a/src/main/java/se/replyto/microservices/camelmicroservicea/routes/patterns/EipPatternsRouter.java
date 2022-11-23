package se.replyto.microservices.camelmicroservicea.routes.patterns;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.replyto.microservices.camelmicroservicea.routes.patterns.Processor;

//@Component
public class EipPatternsRouter extends RouteBuilder {

    BeanIODataFormat inboundDataFormat = new BeanIODataFormat("MessageBeanIOMapping.xml","MessageStream");
    Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void configure() throws Exception {

        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
        jacksonDataFormat.setPrettyPrint(true);

        CsvDataFormat csvFormat = new CsvDataFormat();
        csvFormat.setSkipHeaderRecord(true);
        csvFormat.setDelimiter(',');
        csvFormat.setUseOrderedMaps(true);
        csvFormat.setAllowMissingColumnNames(false);
        csvFormat.setIgnoreSurroundingSpaces(true);




        from("file:files/input")
                .routeId("legacyFileRouteId")
                .unmarshal(csvFormat)
                .log(LoggingLevel.INFO, "Original body : ${body}")
                .split(body().tokenize("\n",1,true))
                .streaming()
                .unmarshal(inboundDataFormat)
                .process(new Processor())
                .marshal(jacksonDataFormat)
                .log(LoggingLevel.INFO, "Transformed body : ${body}")
                .to("activemq:my-activemq-queue")
                .end();
    }
}