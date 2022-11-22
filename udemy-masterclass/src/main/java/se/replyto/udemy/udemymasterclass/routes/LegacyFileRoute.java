package se.replyto.udemy.udemymasterclass.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.replyto.udemy.udemymasterclass.beans.InboundNameAddress;
import se.replyto.udemy.udemymasterclass.processor.NameAddressProcessor;

@Component
public class LegacyFileRoute extends RouteBuilder {

    BeanIODataFormat inboundDataFormat = new BeanIODataFormat("InboundMessageBeanIOMapping.xml","inboundMessageStream");
    Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void configure() throws Exception {

        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
        jacksonDataFormat.setPrettyPrint(true);

        CsvDataFormat csvFormat = new CsvDataFormat();
        csvFormat.setSkipHeaderRecord(true);
        csvFormat.isCaptureHeaderRecord();
        csvFormat.setDelimiter(',');
        csvFormat.setUseOrderedMaps(true);
        csvFormat.setAllowMissingColumnNames(false);
        csvFormat.setIgnoreSurroundingSpaces(true);

        from("file:files/input")
                .routeId("legacyFileRouteId")
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