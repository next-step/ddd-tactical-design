package kitchenpos.product.application.port.out;

import kitchenpos.product.domain.Product;

import java.util.UUID;

public interface LoadProductPort {
    Product loadProduct(final UUID id); // TODO: ID Wrapper 클래스 생성
}
