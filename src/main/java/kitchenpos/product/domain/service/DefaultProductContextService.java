package kitchenpos.product.domain.service;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;
import kitchenpos.menu.application.ProductContextProvider;
import kitchenpos.menu.domain.model.MenuProductQty;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.model.ProductId;
import kitchenpos.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultProductContextService implements ProductContextProvider {

    private final ProductRepository productRepository;

    public DefaultProductContextService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public BigDecimal getTotalPrice(ProductId productId, MenuProductQty qty) {
        Product product = productRepository.findByProductId(productId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_PRODUCT.toString()));

        return product.getTotalPrice(qty.get());
    }

    @Transactional(readOnly = true)
    @Override
    public void validateProduct(List<ProductId> productIds, int menuProductsSize) {
        var products = productRepository.findAllByProductIdIn(productIds);

        if (products.size() != menuProductsSize) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_ANY_PRODUCT.toString());
        }
    }
}
