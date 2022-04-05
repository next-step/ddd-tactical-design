package kitchenpos.menus.domain.tobe.domain.policy;

import kitchenpos.common.policy.NamingRule;
import kitchenpos.menus.exception.MenuGroupNamingRuleViolationException;
import kitchenpos.products.exception.ProductNamingRuleViolationException;
import kitchenpos.products.infra.PurgomalumClient;

import java.util.Objects;

public class DefaultMenuGroupNamingRule implements NamingRule {

    public DefaultMenuGroupNamingRule() { }

    @Override
    public boolean checkRule(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new MenuGroupNamingRuleViolationException("정책에 위반되는 메뉴그룹명 입니다");
        }
        return true;
    }
}
