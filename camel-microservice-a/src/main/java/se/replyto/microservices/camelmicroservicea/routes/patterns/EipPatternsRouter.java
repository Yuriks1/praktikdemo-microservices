package se.replyto.microservices.camelmicroservicea.routes.patterns;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class EipPatternsRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {

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

        from("file:files/csv")
                .unmarshal(csvFormat)
                .process(exchange -> {
                    System.out.println(exchange.getIn().getBody());
                })
                .split(body())
                .pipeline()
                .to("activemq:split-queue")
                .end();


        /*{ "source": "USD",
                "dest": "INR",
                "convRate": 70
        }*/

    }
}
