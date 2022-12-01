package se.replyto.microservices.xmluppgift.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.dataformat.BindyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.replyto.microservices.xmluppgift.beans.InboundCurrencyExchange;
import se.replyto.microservices.xmluppgift.beans.OutboundCsvExchange;
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
        xmlDataFormat.setPrettyPrint(true);
        xmlDataFormat.setIgnoreJAXBElement(false);

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


        JacksonDataFormat jsonDataFormat = new JacksonDataFormat(OutboundCurrencyExchange.class);
        jsonDataFormat.setPrettyPrint(true);



        from("file:files/xml")
//                .split()
//                .simple("${body}")

                .routeId("currencyExchangeRouteId")
                .log(LoggingLevel.INFO, "Original body : ${body}")
                .unmarshal(xmlDataFormat)
                .log(LoggingLevel.INFO, "After XML unmarshal, body : ${body}")

                .multicast()
                    .log(LoggingLevel.INFO, "Before CSV route call : ${body}")
                    .to("direct:csv")
                    .log(LoggingLevel.INFO, "Before JSON route call : ${body}")
                    .to("direct:json")
                    .log(LoggingLevel.INFO, "After JSON route call : ${body}")
                .end();


        from("direct:csv")
                .doTry()
                .process(new ProcessorCsv())
                .marshal()
                .bindy(BindyType.Csv, OutboundCsvExchange.class)
                .log(LoggingLevel.INFO, "New body Csv : ${body}")
                .to("file:files/output?fileName=1000.csv")
                .doCatch(Exception.class)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                        System.out.println(cause);
                    }
                })
                .end();


        from("direct:json")
               .doTry()
                .log(LoggingLevel.INFO, "Before json processor : ${body}")
                .process(new Processor())
                .log(LoggingLevel.INFO, "After json processor : ${body}")
                .marshal(jsonDataFormat)
                .log(LoggingLevel.INFO, "New body Json : ${body}")
                .to("activemq:jsonOut")
                .doCatch(Exception.class)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                        System.out.println(cause);
                    }
                })
                .end();

    }

}



