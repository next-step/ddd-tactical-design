package kitchenpos.core.shared.data.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kitchenpos.core.shared.value.Money;

import java.math.BigDecimal;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, BigDecimal> {
    @Override
    public BigDecimal convertToDatabaseColumn(Money money) {
        return money.getAmount();
    }

    @Override
    public Money convertToEntityAttribute(BigDecimal amount) {
        return Money.wons(amount);
    }
}