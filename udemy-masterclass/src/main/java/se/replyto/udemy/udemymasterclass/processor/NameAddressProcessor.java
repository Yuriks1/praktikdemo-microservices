package se.replyto.udemy.udemymasterclass.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import se.replyto.udemy.udemymasterclass.beans.InboundNameAddress;
import se.replyto.udemy.udemymasterclass.beans.OutboundNameAddress;

public class NameAddressProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        InboundNameAddress inboundNameAddress = exchange.getIn().getBody(InboundNameAddress.class);
        exchange.getIn().setBody(new OutboundNameAddress(inboundNameAddress.getName(),returnFormattedAddress(inboundNameAddress)));
    }

    private String returnFormattedAddress(InboundNameAddress nameAddress) {
        StringBuilder concatenatedAddress = new StringBuilder(200);
        concatenatedAddress.append(nameAddress.getHouseNumber());
        concatenatedAddress.append(" " + nameAddress.getCity() + ",");
        concatenatedAddress.append(" " + nameAddress.getProvince());
        concatenatedAddress.append(" " + nameAddress.getPostalCode());
        return concatenatedAddress.toString();
    }
}