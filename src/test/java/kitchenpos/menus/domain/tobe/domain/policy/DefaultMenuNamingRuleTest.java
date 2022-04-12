package kitchenpos.menus.domain.tobe.domain.policy;

import kitchenpos.support.exception.NamingRuleViolationException;
import kitchenpos.support.policy.NamingRule;
import kitchenpos.products.application.FakePurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultMenuNamingRuleTest {

    private NamingRule menuNamingRule;

    @BeforeEach
    void setUp() {
        menuNamingRule = new DefaultMenuNamingRule(new FakePurgomalumClient());
    }

    @DisplayName("메뉴명은 빈 값이 아니고, 비속어가 포함되지 않아야 한다")
    @Test
    void checkRule_success() {
        //given, when
        boolean result = menuNamingRule.checkRule("후라이드치킨");

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("메뉴명이 빈 값이거나, 욕설 혹은 비속어가 포함될 경우 메뉴명 정책 위반이다")
    @ValueSource(strings = {"욕설", "비속어"})
    @NullSource
    @ParameterizedTest
    void checkRule_fail(final String name) {
        //given, when,then
        assertThatThrownBy(() -> menuNamingRule.checkRule(name))
                .isInstanceOf(NamingRuleViolationException.class);
    }

}
