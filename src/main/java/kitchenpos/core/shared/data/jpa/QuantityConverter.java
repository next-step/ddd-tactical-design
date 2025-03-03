package kitchenpos.core.shared.data.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kitchenpos.core.shared.value.Money;
import kitchenpos.core.shared.value.Quantity;

import java.math.BigDecimal;

@Converter(autoApply = true)
public class QuantityConverter implements AttributeConverter<Quantity, Long> {
    @Override
    public Long convertToDatabaseColumn(Quantity quantity) {
        return quantity.getValue();
    }

    @Override
    public Quantity convertToEntityAttribute(Long quantity) {
        if(quantity == null) {
            return Quantity.ZERO;
        }
        return Quantity.of(quantity);
    }
}