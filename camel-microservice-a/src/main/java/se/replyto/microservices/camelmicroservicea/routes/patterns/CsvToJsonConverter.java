package se.replyto.microservices.camelmicroservicea.routes.patterns;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import se.replyto.microservices.camelmicroservicea.routes.jsoncsvconverter.OrderLineForCsv;

import java.io.File;
import java.io.IOException;

public class CsvToJsonConverter {



    public static void csvToJson(File csvFile, File jsonFile) throws IOException {

        CsvSchema orderLineSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<CurrencyExchange> orderLines = csvMapper.readerFor(CurrencyExchange.class)
                .with(orderLineSchema)
                .readValues(csvFile);

        new ObjectMapper()
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .writeValue(jsonFile, orderLines.readAll());
    }

    public static void JsonToFormattedCsv(File jsonFile, File csvFile) throws IOException {

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper
                .schemaFor(OrderLineForCsv.class)
                .withHeader();
        csvMapper.addMixIn(CurrencyExchange.class, OrderLineForCsv.class);

        CurrencyExchange[] orderLines = new ObjectMapper()
                .readValue(jsonFile, CurrencyExchange[].class);
        csvMapper.writerFor(CurrencyExchange[].class)
                .with(csvSchema)
                .writeValue(csvFile, orderLines);
    }
}