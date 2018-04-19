package Json.formatters;


import Json.JSONTypeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class DateFormatter implements JSONTypeFormatter<Date> {


    @Override
    public String format(Date date, Json.JSONFormatter formatter, Map<String, Integer> ctx) {
        return  "\"" + new SimpleDateFormat("dd.MM.yyyy").format(date)
                + "\"";    }
}
