package kitchenpos.products.domain.tobe.domain.policy;

import kitchenpos.common.policy.PricingRule;
import kitchenpos.products.exception.ProductPricingRuleViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultProductPricingRuleTest {

    private PricingRule productPricingRule;

    @BeforeEach
    void setUp() {
        productPricingRule = new DefaultProductPricingRule();
    }

    @DisplayName("상품가격은 0원 이상이어야 한다")
    @ValueSource(strings = {"16000","0"})
    @ParameterizedTest
    void checkRule_success(final BigDecimal price) {
        //given, when
        boolean result = productPricingRule.checkRule(price);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("상품가격이 음수이거나, 빈 값일 경우 상품가격 정책 위반이다")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void checkRule_fail(final BigDecimal price) {
        //given, when,then
        assertThatThrownBy(() -> productPricingRule.checkRule(price))
                .isInstanceOf(ProductPricingRuleViolationException.class);
    }
}
