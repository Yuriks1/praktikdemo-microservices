package se.replyto.microservices.camelmicroservicea.routes.udemy;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;
import se.replyto.microservices.camelmicroservicea.routes.patterns.CurrencyExchange;

import java.util.ArrayList;


@Component
public class PatternsRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("file:files/aggregate-json")
                .unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
                .aggregate(simple("${body.to}"), new ArrayListAggregationStrategy())
                .completionSize(3)
                //.completionTimeout(HIGHEST)
                .to("log:aggregate-json");



    }

    private class ArrayListAggregationStrategy implements org.apache.camel.AggregationStrategy {
        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            Object newBody = newExchange.getIn().getBody();
            ArrayList<Object> list = null;
            if (oldExchange == null) {
                list = new ArrayList<Object>();
                list.add(newBody);
                newExchange.getIn().setBody(list);
                return newExchange;
            } else {
                list = oldExchange.getIn().getBody(ArrayList.class);
                list.add(newBody);
                return oldExchange;
            }
        }
    }
}
