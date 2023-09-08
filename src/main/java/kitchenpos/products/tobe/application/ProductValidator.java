package kitchenpos.products.tobe.application;

import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductValidator {

    private final ProductRepository productRepository;

    public ProductValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public void isExistProductIn(MenuProducts menuProducts) {
        final List<Product> products = productRepository.findAllByIdIn(menuProducts.getProductIds());

        if (products.size() != menuProducts.size()) {
            throw new IllegalArgumentException();
        }
    }
}
