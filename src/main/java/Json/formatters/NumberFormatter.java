package Json.formatters;


import Json.JSONTypeFormatter;

import java.util.Map;

public class NumberFormatter implements JSONTypeFormatter<Number> {


    @Override
    public String format(Number number, Json.JSONFormatter formatter, Map<String, Integer> ctx) {
        return (number.toString());
    }
}
