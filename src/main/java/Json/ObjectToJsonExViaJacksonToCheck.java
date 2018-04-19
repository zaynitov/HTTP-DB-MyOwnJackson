package Json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ObjectToJsonExViaJacksonToCheck {

    public String objToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = null;
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        mapper.setDateFormat(df);
        jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        return jsonStr;

    }

}
