package kitchenpos.menus.domain.tobe.domain.policy;

import kitchenpos.common.policy.NamingRule;
import kitchenpos.products.exception.ProductNamingRuleViolationException;
import kitchenpos.products.infra.PurgomalumClient;

import java.util.Objects;

public class DefaultMenuGroupNamingRule implements NamingRule {

    private final PurgomalumClient purgomalumClient;

    public DefaultMenuGroupNamingRule(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public boolean checkRule(String name) {
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new ProductNamingRuleViolationException("잘못된 상품 명입니다");
        }
        return true;
    }
}
