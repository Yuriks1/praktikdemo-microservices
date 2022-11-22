package se.replyto.udemy.udemymasterclass.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import se.replyto.udemy.udemymasterclass.beans.InboundNameAddress;
import se.replyto.udemy.udemymasterclass.beans.OutboundNameAddress;

public class NameAddressProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        InboundNameAddress inboundNameAddress = exchange.getIn().getBody(InboundNameAddress.class);
        exchange.getIn().setBody(new OutboundNameAddress(inboundNameAddress.getName(),changeInformation(inboundNameAddress)));
    }

    private String changeInformation(InboundNameAddress nameAddress) {
        StringBuilder concatenatedAddress = new StringBuilder(10);
        concatenatedAddress.append(" " + nameAddress.getCity());
        return concatenatedAddress.toString();
    }
}