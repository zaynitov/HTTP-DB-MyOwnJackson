package Json.formatters;


import Json.JSONFormatter;
import Json.JSONTypeFormatter;

import java.util.Map;

public class StringFormatter implements JSONTypeFormatter<String> {
    @Override
    public String format(String s, JSONFormatter formatter, Map<String, Integer> ctx) {
        return ("\"" + s.toString() + "\"");
    }
}
