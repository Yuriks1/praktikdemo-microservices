package se.replyto.microservices.camelmicroservicea.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.crypto.CryptoDataFormat;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;


//@Component
public class ActiveMqSenderRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Enable tracing
        getContext().setTracing(true);

        //dead letter queue
        errorHandler(deadLetterChannel("activemq:dead-letter-queue"));

        /*from("timer:active-mq-timer?period=5000")
                .transform().constant("My message for Active MQ")
                .log("${body}")
                .marshal(createEncryptor())
//                .setProperty("myProperty", constant("MyProperty"))
//                .setHeader("myHeader", constant("MyHeader"))
                .to("activemq:my-activemq-queue");*/


        from("file:files/json?fileName=daltons.json")
                .log("${body}")

                .errorHandler(deadLetterChannel("mock:error")
                        .maximumRedeliveries(3)
                        .redeliveryDelay(1000)
                        .backOffMultiplier(2)
                        .useOriginalMessage()
                        .useExponentialBackOff())
                .to("activemq:my-activemq-queue");

        /*from("file:files/xml")
                .log("${body}")
                .to("activemq:my-activemq-xml-queue");
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
