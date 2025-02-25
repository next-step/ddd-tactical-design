package kitchenpos.shared.infra.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kitchenpos.shared.domain.Money;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, Long> {
    @Override
    public Long convertToDatabaseColumn(Money money) {
        return money.amount().longValue();
    }

    @Override
    public Money convertToEntityAttribute(Long amount) {
        return Money.of(amount);
    }
}