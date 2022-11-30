package se.replyto.microservices.xmluppgift.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.replyto.microservices.xmluppgift.beans.InboundCurrencyExchange;
import se.replyto.microservices.xmluppgift.beans.OutboundCurrencyExchange;
import se.replyto.microservices.xmluppgift.processor.Processor;
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
        csvFormat.setIgnoreEmptyLines(true);
        csvFormat.setIgnoreHeaderCase(true);
        csvFormat.setIgnoreSurroundingSpaces(true);


        JacksonDataFormat jsonDataFormat = new JacksonDataFormat(OutboundCurrencyExchange.class);
        jsonDataFormat.setPrettyPrint(true);


        from("file:files/xml")

                .routeId("currencyExchangeRouteId")
                .log(LoggingLevel.INFO, "Original body : ${body}")

                .unmarshal(xmlDataFormat)
                .split(body())
                .multicast()

                .to("direct:csv", "direct:json")
                .end();




        from("direct:csv")
                .doTry()


                .process(new ProcessorCsv())
                .marshal(csvFormat)
                .log(LoggingLevel.INFO, "New body Csv : ${body}")
                .to("file:files/output?fileName=1000.csv")
                .doCatch(Exception.class)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                        System.out.println(cause);
                    }
                }
                )
                .end();


        from("direct:json")
                .doTry()

                .process(new Processor())
                .marshal(jsonDataFormat)
                .log(LoggingLevel.INFO, "New body Json : ${body}")
                .to("activemq:jsonOut")
                .doCatch(Exception.class)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                        System.out.println(cause);
                    }
                })              .end();

    }
}



