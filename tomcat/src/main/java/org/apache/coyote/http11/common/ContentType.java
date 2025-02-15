package org.apache.coyote.http11.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static org.apache.coyote.http11.common.ContentTypeName.*;

public class ContentType {

    private static final String CHARSET_PARAMETER_KEY = "charset";
    private static final String DELIMITER = "; ";
    private static final String PARAMETER_VALUE_DELIMITER = "=";

    private ContentTypeName type = TEXT_HTML;
    private final Map<String, String> values = new HashMap<>();

    public ContentType(String contentType) {
        String[] values = contentType.split(DELIMITER);

        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                this.type = from(values[0]);
                continue;
            }
            String[] parameterValue = values[i].split(PARAMETER_VALUE_DELIMITER);
            this.values.put(parameterValue[0], parameterValue[1]);
        }
    }

    public String getType() {
        return type.getName();
    }

    public String getParameter(String parameter) {
        return values.get(parameter);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getType());

        values.putIfAbsent(CHARSET_PARAMETER_KEY, Charset.getDefault());

        for (Entry<String, String> values : this.values.entrySet()) {
            stringBuilder.append(DELIMITER).append(values.getKey())
                    .append(PARAMETER_VALUE_DELIMITER)
                    .append(values.getValue());
        }

        return stringBuilder.toString();
    }
}
