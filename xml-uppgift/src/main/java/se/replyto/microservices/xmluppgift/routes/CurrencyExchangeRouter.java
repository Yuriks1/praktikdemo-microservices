package se.replyto.microservices.xmluppgift.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.replyto.microservices.xmluppgift.beans.InboundCurrencyExchange;
import se.replyto.microservices.xmluppgift.processor.ProcessorCsv;

import javax.xml.bind.JAXBContext;

@Component
public class CurrencyExchangeRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        Logger logger = LoggerFactory.getLogger(getClass());


        JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
        JAXBContext con = JAXBContext.newInstance(InboundCurrencyExchange.class);
        xmlDataFormat.setContext(con);

        validator()
                .type("xml")
                .withUri("validator:InboundCurrencyExchange.xsd");


        CsvDataFormat csvFormat = new CsvDataFormat();
        csvFormat.setSkipHeaderRecord(true);
        csvFormat.setDelimiter(',');
        csvFormat.setUseOrderedMaps(true);
        csvFormat.setAllowMissingColumnNames(false);
        csvFormat.setIgnoreSurroundingSpaces(true);


        from("file:files/xml")

                .routeId("currencyExchangeRouteId")
                .log(LoggingLevel.INFO, "Original body : ${body}")
                .unmarshal(xmlDataFormat)
                .process(new ProcessorCsv())
                //.marshal(jsonDataFormat)
                .marshal(csvFormat)
                .log(LoggingLevel.INFO, "New body : ${body}")
                .to("activemq:my-activemq-queue");


    }
}
