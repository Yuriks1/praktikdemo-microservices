package se.replyto.microservices.camelmicroserviceb.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.crypto.CryptoDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.replyto.microservices.camelmicroserviceb.CurrencyExchange;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.*;
import java.security.cert.CertificateException;


@Component
public class ActiveMqReceiverRouter extends RouteBuilder {

    //@Autowired
     MyCurrencyExchangeProcessor myCurrencyExchangeProcessor;
    //@Autowired
     MyCurrencyExchangeProcessorTransformer myCurrencyExchangeProcessorTransformer;

    @Override
    public void configure() throws Exception {




        from("activemq:my-activemq-queue")
                //.unmarshal(createEncryptor())
//                .unmarshal().json(JsonLibrary.Jackson,CurrencyExchange.class)
//                .bean(myCurrencyExchangeProcessor)
//                .bean(myCurrencyExchangeProcessorTransformer)
                .to("log:received-message-from-active-mq");


//        from("activemq:my-activemq-xml-queue")
//                .unmarshal()
//                .jacksonXml(CurrencyExchange.class)
//                .bean(myCurrencyExchangeProcessor)
//                .bean(myCurrencyExchangeProcessorTransformer)
//                .to("log:received-message-from-active-xml-mq");

      /*  from("activemq:split-queue")

                .to("log:received-message-from-active-mq");
*/


    }


    private CryptoDataFormat createEncryptor() throws KeyStoreException, IOException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException, java.security.cert.CertificateException {
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        ClassLoader classLoader = getClass().getClassLoader();
        keyStore.load(classLoader.getResourceAsStream("myDesKey.jceks"), "someKeystorePassword".toCharArray());
        Key sharedKey = keyStore.getKey("myDesKey", "someKeyPassword".toCharArray());

        return new CryptoDataFormat("DES", sharedKey);
    }



}

//@Component
class MyCurrencyExchangeProcessor {
    Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeProcessor.class);

    public void processMessage(CurrencyExchange currencyExchange) {
        logger.info("Do some processing with currencyExchange.getConversionMultiple() value which is   {}",
                currencyExchange.getConversionMultiple());
    }
}

//@Component
class MyCurrencyExchangeProcessorTransformer {
    Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeProcessor.class);

    public CurrencyExchange processMessage(CurrencyExchange currencyExchange) {


        currencyExchange.setConversionMultiple(currencyExchange.getConversionMultiple()
                .multiply(BigDecimal.TEN));

        logger.info("Do some processing with currencyExchange.getConversionMultiple() value which is   {}",
                currencyExchange.getConversionMultiple());
        return currencyExchange;
    }
}


