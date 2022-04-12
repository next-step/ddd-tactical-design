package kitchenpos.products.domain.tobe.domain.policy;

import kitchenpos.support.exception.NamingRuleViolationException;
import kitchenpos.support.policy.NamingRule;
import kitchenpos.support.infra.Profanity;

import java.util.Objects;

public class DefaultProductNamingRule implements NamingRule {

    private final Profanity purgomalumClient;

    public DefaultProductNamingRule(Profanity purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public boolean checkRule(String name) {
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new NamingRuleViolationException("잘못된 상품 명입니다");
        }
        return true;
    }
}
