package kitchenpos.core.products.tobe.domain.exception;

import kitchenpos.core.shared.identifier.ProductId;

public class ProductNotFoundException extends ProductException {

    private final ProductId id;

    public ProductNotFoundException(ProductId id) {
        super("해당 id를 가진 Product를 찾을 수 없습니다. id: " + id.getValue());
        this.id = id;
    }

    public ProductId getId() {
        return id;
    }
}
