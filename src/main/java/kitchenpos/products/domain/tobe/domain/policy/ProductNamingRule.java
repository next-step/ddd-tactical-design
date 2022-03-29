package kitchenpos.products.domain.tobe.domain.policy;

import kitchenpos.products.infra.PurgomalumClient;

public interface ProductNamingRule {
    void checkRule(String name);
}
