package kitchenpos.core.shared.data.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kitchenpos.core.shared.value.Money;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, Long> {
    @Override
    public Long convertToDatabaseColumn(Money money) {
        return money.getAmount().longValue();
    }

    @Override
    public Money convertToEntityAttribute(Long amount) {
        return Money.wons(amount);
    }
}