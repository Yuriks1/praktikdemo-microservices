package se.replyto.microservices.camelmicroservicea.routes.patterns;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class EipPatternsRouter extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
        jacksonDataFormat.setPrettyPrint(true);

        String inputDirectory = "file:files/csv";
        String outputDirectory = "activemq:split-queue";


        CsvDataFormat csvFormat = new CsvDataFormat();
        csvFormat.setSkipHeaderRecord(true);
        csvFormat.isCaptureHeaderRecord();
        csvFormat.setDelimiter(',');
        csvFormat.setUseOrderedMaps(true);
        csvFormat.setAllowMissingColumnNames(false);
        csvFormat.setIgnoreSurroundingSpaces(true);

        /*
        pipeline
        Content Based Router = choice()
        Multicast

                from("timer:multicast?period=10000")
                        .multicast()
                        .to("log:example1", "log:example2", "log:example3");
        */


        from(inputDirectory)
                /*.unmarshal(csvFormat)
                .process(exchange -> {
                    System.out.println(exchange.getIn().getBody());
                })
                .split(body())
*/
                .unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
                /*.marshal(jacksonDataFormat)
                .transform(body().regexReplaceAll("from", "source"))
                .transform(body().regexReplaceAll("to", "dest"))
                .transform(body().regexReplaceAll("conversionMultiple", "convRate"))*/
                //.setBody(body().prepend("Here you can see currency exchange info:\n"))*/
                //.routeId("csv-to-json")
                //.pipeline()
                .to(outputDirectory);
                //.end();


        /*{ "source": "USD",
                "dest": "INR",
                "convRate": 70
        }*/


    }
}




