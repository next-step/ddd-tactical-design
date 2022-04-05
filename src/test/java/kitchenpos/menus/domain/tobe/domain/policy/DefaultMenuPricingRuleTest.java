package kitchenpos.menus.domain.tobe.domain.policy;

import kitchenpos.Fixtures;
import kitchenpos.common.exception.PricingRuleViolationException;
import kitchenpos.common.policy.PricingRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultMenuPricingRuleTest {
    private PricingRule menuPricingRule;

    @BeforeEach
    void setUp() {
        menuPricingRule = new DefaultMenuPricingRule(Fixtures.tobeMenuProducts("후라이드",16_000L));
    }

    @DisplayName("메뉴가격은 0원 이상이어야 한다")
    @ValueSource(strings = {"16000","0"})
    @ParameterizedTest
    void checkRule_success(final BigDecimal price) {
        //given, when
        boolean result = menuPricingRule.checkRule(price);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("메뉴가격이 음수이거나, 빈 값일 경우 상품가격 정책 위반이다")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void checkRule_fail(final BigDecimal price) {
        //given, when,then
        assertThatThrownBy(() -> menuPricingRule.checkRule(price))
                .isInstanceOf(PricingRuleViolationException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @ValueSource(strings = "100000")
    @ParameterizedTest
    void createExpensiveMenu(final BigDecimal price) {
        assertThatThrownBy(() -> menuPricingRule.checkRule(price))
                .isInstanceOf(PricingRuleViolationException.class);
    }
}
