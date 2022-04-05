package kitchenpos.products.domain.tobe.domain.policy;

import kitchenpos.common.exception.NamingRuleViolationException;
import kitchenpos.common.policy.NamingRule;
import kitchenpos.products.application.FakePurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultProductNamingRuleTest {

    private NamingRule productNamingRule;

    @BeforeEach
    void setUp() {
        productNamingRule = new DefaultProductNamingRule(new FakePurgomalumClient());
    }

    @DisplayName("상품명이 빈 값이 아니고, 비속어가 포함되지 않아야 한다")
    @Test
    void checkRule_success() {
        //given, when
        boolean result = productNamingRule.checkRule("후라이드치킨");

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("상품명이 빈 값이거나, 욕설 혹은 비속어가 포함될 경우 상품가격 정책 위반이다")
    @ValueSource(strings = {"욕설","비속어"})
    @NullSource
    @ParameterizedTest
    void checkRule_fail(final String name) {
        //given, when,then
        assertThatThrownBy(() -> productNamingRule.checkRule(name))
                .isInstanceOf(NamingRuleViolationException.class);
    }
}
