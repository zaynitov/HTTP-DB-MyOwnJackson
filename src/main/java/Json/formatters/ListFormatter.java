package Json.formatters;

import Json.JSONFormatterImpl;
import Json.JSONTypeFormatter;

import java.util.List;
import java.util.Map;

public class ListFormatter implements JSONTypeFormatter<List> {



    @Override
    public String format(List list, Json.JSONFormatter formatter, Map<String, Integer> ctx) {
        Object[] objects = list.toArray();


        return new JSONFormatterImpl().arrayToString(objects,ctx);
    }
}
