package se.replyto.udemy.udemymasterclass.routes.fileroute;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
public class LegacyFileRoute extends RouteBuilder {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void configure() throws Exception {

        from("file:files/input?fileName=inputFile.csv")
                .routeId("legacyFileRouteId")
                .split(body().tokenize("\n",1,true))
                .process(exchange -> {
                    String filedata = exchange.getIn().getBody(String.class);
                    logger.info("This is the read fileData: " + filedata);

                })
                .to("file:files/output?fileName=outputFile.csv&fileExist=Append&appendChars=\\n")
                .end();
    }
}