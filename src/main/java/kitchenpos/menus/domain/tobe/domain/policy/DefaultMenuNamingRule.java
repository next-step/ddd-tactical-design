package kitchenpos.menus.domain.tobe.domain.policy;

import kitchenpos.common.exception.NamingRuleViolationException;
import kitchenpos.common.policy.NamingRule;
import kitchenpos.products.infra.PurgomalumClient;

import java.util.Objects;

public class DefaultMenuNamingRule implements NamingRule {

    private final PurgomalumClient purgomalumClient;

    public DefaultMenuNamingRule(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public boolean checkRule(String name) {
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new NamingRuleViolationException("잘못된 메뉴명입니다");
        }
        return true;
    }
}
