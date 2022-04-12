package kitchenpos.menus.domain.tobe.domain.policy;

import kitchenpos.support.exception.NamingRuleViolationException;
import kitchenpos.support.policy.NamingRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultMenuGroupNamingRuleTest {

    private NamingRule menuGroupNamingRule;

    @BeforeEach
    void setUp() {
        menuGroupNamingRule = new DefaultMenuGroupNamingRule();
    }

    @DisplayName("메뉴그룹명은 빈 값일 수 없다")
    @Test
    void checkRule_success() {
        //given, when
        boolean result = menuGroupNamingRule.checkRule("후라이드치킨");

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("메뉴그룹명이 빈 값일 경우 메뉴그룹명 정책 위반이다")
    @ValueSource(strings = {""})
    @NullSource
    @ParameterizedTest
    void checkRule_fail(final String name) {
        //given, when,then
        assertThatThrownBy(() -> menuGroupNamingRule.checkRule(name))
                .isInstanceOf(NamingRuleViolationException.class);
    }
}
