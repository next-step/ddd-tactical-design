package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.domain.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EatInMenuTest {

    @Test
    void create() {
        UUID id = UUID.randomUUID();

        EatInMenu result = EatInMenu.create(id, Price.of(BigDecimal.valueOf(100L)), true);

        assertThat(result).isEqualTo(EatInMenu.create(id, Price.of(BigDecimal.valueOf(100L)), true));
    }

}