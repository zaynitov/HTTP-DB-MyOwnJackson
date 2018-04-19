package Json;


import java.util.Map;

public interface JSONTypeFormatter<T> {
    String format(T t, JSONFormatter formatter, Map<String, Integer> ctx);
}
