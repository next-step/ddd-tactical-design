package kitchenpos.product.domain;

import kitchenpos.support.vo.Name;

public interface ProductNameFactory {

    /**
     * @throws IllegalArgumentException nameCandidate에 비속어가 포함되어 있을 때
     */
    ProductName create(final Name nameCandidate);
}
