package kitchenpos.products.application;

import kitchenpos.common.annotation.Validator;
import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;

import java.util.List;
import java.util.UUID;

import static kitchenpos.common.exception.KitchenPosExceptionType.NOT_FOUND;

@Validator
public class ProductValidator {

    private final ProductRepository productRepository;

    public ProductValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void isExistProducts(List<UUID> productIds) {
        List<Product> products = productRepository.findAllByIdIn(productIds);

        if (products.size() != productIds.size()) {
            String message = String.format("상품=%s 중 일부를 ", productIds);
            throw new KitchenPosException(message, NOT_FOUND);
        }
    }
}
