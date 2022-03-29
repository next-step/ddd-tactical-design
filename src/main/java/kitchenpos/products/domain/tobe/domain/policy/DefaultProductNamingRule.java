package kitchenpos.products.domain.tobe.domain.policy;

import kitchenpos.products.exception.ProductNamingRuleViolationException;
import kitchenpos.products.infra.PurgomalumClient;

import java.util.Objects;

public class DefaultProductNamingRule implements ProductNamingRule {

    private final PurgomalumClient purgomalumClient;

    public DefaultProductNamingRule(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public void checkRule(String name) {
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new ProductNamingRuleViolationException("잘못된 상품 명입니다");
        }
    }
}
