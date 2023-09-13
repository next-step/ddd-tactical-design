package kitchenpos.menus.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MenuProductModelTest {

    @Test
    void amount() {
        MenuProductModel menuProductModel = new MenuProductModel(UUID.randomUUID(), new BigDecimal(10_000), 3);
        BigDecimal amount = menuProductModel.amount();
        assertThat(amount).isEqualTo(new BigDecimal(30_000));

    }
}