package kitchenpos.menus.menu.tobe.domain;

import kitchenpos.menus.menu.tobe.domain.vo.ProductSpecification;

import java.util.UUID;

public interface ProductContextClient {

    ProductSpecification findProductById(final UUID productId);
}
