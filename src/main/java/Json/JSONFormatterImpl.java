package Json;
import Json.formatters.*;
import java.lang.reflect.Field;
import java.util.*;

import static Json.StringSPaces.getStringOfSpaces;


public class JSONFormatterImpl implements JSONFormatter {
    private Map<Class, JSONTypeFormatter> types = new HashMap<>();

    public JSONFormatterImpl() {
        types.put(Date.class, new DateFormatter());
        types.put(GregorianCalendar.class, new CalendarFormatter());
        types.put(Calendar.class, new CalendarFormatter());
        types.put(String.class, new StringFormatter());
        types.put(ArrayList.class, new ListFormatter());
        types.put(LinkedList.class, new ListFormatter());
        types.put(HashSet.class, new SetFormatter());
        types.put(TreeSet.class, new SetFormatter());
        types.put(Short.class, new NumberFormatter());
        types.put(Integer.class, new NumberFormatter());
        types.put(Long.class, new NumberFormatter());
        types.put(Double.class, new NumberFormatter());
        types.put(Float.class, new NumberFormatter());
    }

    @Override
    public String marshall(Object obj) {
        StringBuilder resultString = new StringBuilder("{\r\n");

        if (obj == null)
            return "";


        Map<String, Integer> ctx = new HashMap<>();
        ctx.put("bottom", 2);

        if (obj.getClass().isArray()) {
            return arrayToString((Object[]) obj, ctx);
        }


        if (types.containsKey(obj.getClass())) {
            int preValue = ctx.get("bottom");
            ctx.put("bottom", preValue + 2);


            return types.get(obj.getClass()).format(obj, this, ctx);
        }

        Class className = obj.getClass();

        while (className != null) {
            try {
                for (Field field : className.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = field.get(obj);
                    if (value != null) {
                        resultString.append(getStringOfSpaces(ctx.get("bottom")));
                        resultString.append("\"" + field.getName() + "\" : ");


                        if (value.getClass().isArray()) {
                            resultString.append(arrayToString((Object[]) value, ctx));
                        }

                        if (types.containsKey(value.getClass())) {
                            resultString.append(types.get(value.getClass()).format(value, this, ctx)).append(",\n");
                        }


                    }
                }
                className = className.getSuperclass();

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        int lastComma = resultString.lastIndexOf(",");
        if (resultString.substring(lastComma + 1).equals("\n")) {
            resultString.delete(lastComma, lastComma + 1);
        }
        if (resultString.toString().endsWith("\n")) resultString.delete(resultString.length()-1,resultString.length());
        if (resultString.toString().endsWith("\n")) resultString.delete(resultString.length()-1,resultString.length());
        resultString.append("\n}");

        return resultString.toString();
    }


    @Override
    public <T> boolean addType(Class<T> clazz, JSONTypeFormatter<T> format) {
        return false;
    }


    public String arrayToString(Object[] arrayToString, Map<String, Integer> ctx) {
        if (arrayToString.length==0) return "[]";

        StringBuilder resultTermString = new StringBuilder("[\r\n");
        int preValue = ctx.get("bottom");
        ctx.put("bottom", preValue + 2);
        for (int i = 0; i < arrayToString.length; i++) {

            if (i == (arrayToString.length - 1)) {

                resultTermString.append(getStringOfSpaces(ctx.get("bottom")) +
                        marshall(arrayToString[i])+"\r\n");
                resultTermString.delete(resultTermString.length() - 1, resultTermString.length());

                resultTermString.append("\n" + getStringOfSpaces(ctx.get("bottom") - 2) + "]\r\n");

                break;
            }


            resultTermString.append(getStringOfSpaces(ctx.get("bottom")) +
                    marshall(arrayToString[i])+",\r\n");

        }
        preValue = ctx.get("bottom");
        ctx.put("bottom", preValue - 2);
        return resultTermString.toString();
    }
}
