package se.replyto.microservices.camelmicroservicea.routes.jsoncsvconverter;

public class CsvToJsonConverter {



   /* public static void csvToJson(File csvFile, File jsonFile) throws IOException {

        CsvSchema orderLineSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<CurrencyExchange> orderLines = csvMapper.readerFor(CurrencyExchange.class)
                .with(orderLineSchema)
                .readValues(csvFile);

        new ObjectMapper()
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .writeValue(jsonFile, orderLines.readAll());
    }
*/
}
