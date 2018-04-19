package Json.formatters;

import Json.JSONFormatter;
import Json.JSONFormatterImpl;
import Json.JSONTypeFormatter;

import java.util.Map;
import java.util.Set;

public class SetFormatter implements JSONTypeFormatter<Set> {


    @Override
    public String format(Set set, JSONFormatter formatter, Map<String, Integer> ctx) {
        Object[] objects = set.toArray();


        return new JSONFormatterImpl().arrayToString(objects,ctx);

    }
}



