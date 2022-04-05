package kitchenpos.menus.domain.tobe.domain.policy;

import kitchenpos.common.exception.NamingRuleViolationException;
import kitchenpos.common.policy.NamingRule;

import java.util.Objects;

public class DefaultMenuGroupNamingRule implements NamingRule {

    public DefaultMenuGroupNamingRule() { }

    @Override
    public boolean checkRule(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new NamingRuleViolationException("정책에 위반되는 메뉴그룹명 입니다");
        }
        return true;
    }
}
