package kitchenpos.menus.tobe.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import java.math.BigDecimal;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class MenuProductAmountTest {

    @DisplayName("메뉴 상품의 금액을 생성한다.")
    @Test
    void create() {
        BigDecimal productPrice = BigDecimal.ONE;
        MenuProductQuantity quantity = new MenuProductQuantity(2);

        MenuProductAmount menuProductAmount = new MenuProductAmount(productPrice, quantity);

        assertThat(menuProductAmount).isNotNull();
        assertThat(menuProductAmount.getValue()).isEqualTo(BigDecimal.valueOf(2));
    }

    @DisplayName("상품 금액은 비어있을 수 없다.")
    @ParameterizedTest
    @NullSource
    void error(BigDecimal productPrice) {
        assertThatExceptionOfType(IllegalIdentifierException.class)
                .isThrownBy(() -> new MenuProductAmount(productPrice, new MenuProductQuantity(0)));
    }
}
