package kitchenpos.products.domain.tobe.domain;

import kitchenpos.products.domain.tobe.policy.FakeFailProductPricingRule;
import kitchenpos.products.domain.tobe.policy.FakeSuccessProductPricingRule;
import kitchenpos.products.dto.ProductPriceChangeRequest;
import kitchenpos.products.exception.ProductPricingRuleViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.Fixtures.tobeProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TobeProductTest {

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice_success() {
        //given
        final TobeProduct 상품 = tobeProduct("후라이드", BigDecimal.valueOf(16_000L));
        final BigDecimal 가격 = BigDecimal.valueOf(15_000L);
        //when
        상품.changePrice(가격, new FakeSuccessProductPricingRule());

        //then
        assertThat(상품.getPrice()).isEqualTo(가격);
    }

    @DisplayName("상품가격정책에 부합하지 않은 가격으로는 변경할 수 없다.")
    @Test
    void changePrice_fail_policy_violation() {
        //given
        final TobeProduct 상품 = tobeProduct("후라이드", BigDecimal.valueOf(16_000L));
        final BigDecimal 가격 = BigDecimal.valueOf(15_000L);

        //when&then
        assertThatThrownBy(() -> 상품.changePrice(가격, new FakeFailProductPricingRule()))
                .isInstanceOf(ProductPricingRuleViolationException.class);
    }
}
