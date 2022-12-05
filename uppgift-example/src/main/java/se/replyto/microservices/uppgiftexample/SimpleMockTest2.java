package se.replyto.microservices.uppgiftexample;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class SimpleMockTest2 extends RouteBuilder {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void configure() throws Exception {


        CsvDataFormat csvFormat = initCsvFormat();
        JaxbDataFormat jaxbDataFormat = initJaxbXmlFormat();

        from("file:files/xml")
                .routeId("CsvExampleRoute")

                .convertBodyTo(String.class)
                .log("Incoming CSV:\r\n${body}")
                .unmarshal(csvFormat)
                .process(e -> {
                    logger.log(Level.INFO,
                            "Body type: " + e.getMessage().getBody().getClass().getCanonicalName());
                    List<Map<String, String>> body = (List<Map<String, String>>) e.getMessage().getBody();

                    int rowIndex = 1;
                    for (Map<String, String> row : body) {
                        for (String fieldName : row.keySet()) {
                            logger.log(Level.INFO,
                                    "(" + rowIndex + ") - " + fieldName + " = " + row.get(fieldName));
                        }
                        rowIndex++;
                    }
                })

                .log("After unmarshalling CSV:\r\n${body}") //

                .process(e -> {
                    List<Map<String, String>> body = new ArrayList<>();

                    HashMap<String, String> row1 = new HashMap<>();
                    row1.put("FromCurrency", "CC1");
                    row1.put("ToCurrency", "CC2");
                    row1.put("CurrencyRate", "1.23");

                    HashMap<String, String> row2 = new HashMap<>();
                    row2.put("FromCurrency", "CC3");
                    row2.put("ToCurrency", "CC4");
                    row2.put("CurrencyRate", "3.21");

                    body.add(row1);
                    body.add(row2);

                    e.getMessage().setBody(body);
                }).log("Before marshalling CSV:\r\n${body}") //
                .marshal(csvFormat) //
                .log("After marshalling CSV:\r\n${body}") //

                .log("Route finished processing").to("mock:result");
    }

    private JaxbDataFormat initJaxbXmlFormat() {
        JaxbDataFormat jaxbDataFormat = new JaxbDataFormat(true);
        jaxbDataFormat.setContentTypeHeader("true");
        jaxbDataFormat.setEncoding("UTF-8");
        return jaxbDataFormat;
    }

    private CsvDataFormat initCsvFormat() {
        CsvDataFormat csvFormat = new CsvDataFormat();
        csvFormat.setHeader(Arrays.asList("FromCurrency", "ToCurrency", "CurrencyRate"));
        csvFormat.setSkipHeaderRecord("true");
        csvFormat.setDelimiter(";");
        csvFormat.setUseOrderedMaps("true");
        csvFormat.setAllowMissingColumnNames("false");
        csvFormat.setIgnoreSurroundingSpaces("true");
        return csvFormat;
    }


    private String getCsvToSendBody() {
        return "FromCurrency;ToCurrency;CurrencyRate\r\n"
                + "USD;SEK;10.32\r\n"
                + "GBP;NOK;11.96\r\n"
                + "EUR;USD;11.05\r\n";
    }

};

