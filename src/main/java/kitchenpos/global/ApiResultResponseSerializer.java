package kitchenpos.global;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import kitchenpos.global.error.dto.ErrorResponse;

import java.io.IOException;
import java.util.Map;

class ApiResultResponseSerializer extends JsonSerializer<ApiResultResponse<?>> {
    @Override
    public void serialize(ApiResultResponse<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeBooleanField("success", value.isSuccess());
        if (value.isSuccess()) {
            for (Map.Entry<String, ?> entry : value.getData().entrySet()) {
                gen.writeObjectField(entry.getKey(), entry.getValue());
            }
        } else {
            for (Map.Entry<String, ErrorResponse> entry : value.getError().entrySet()) {
                gen.writeObjectField("error", entry.getValue());
            }
        }
        gen.writeEndObject();
    }
}
