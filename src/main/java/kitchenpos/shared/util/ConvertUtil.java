package kitchenpos.shared.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ConvertUtil<T, R> {
    private static final ObjectMapper objectMapper;

    static  {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static <T, R> T convert(R from, Class<T> to) {
        if (Objects.isNull(from)) {
            return null;
        }
        return objectMapper.convertValue(from, to);
    }

    public static <T, R> List<R> convertList(List<T> from, Class<R> to) {
        List<R> result = new ArrayList<>();
        if (from.isEmpty()) {
            return result;
        }
        for (T t : from) {
            result.add(objectMapper.convertValue(t, to));
        }
        return result;
    }
}
