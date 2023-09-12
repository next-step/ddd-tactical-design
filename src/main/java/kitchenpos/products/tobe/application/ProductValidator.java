package kitchenpos.products.tobe.application;

import kitchenpos.menus.tobe.ui.MenuProductRequest;
import kitchenpos.menus.tobe.ui.MenuRequest;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductValidator {

    private final ProductRepository productRepository;

    public ProductValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public void isExistProductIn(MenuRequest request) {
        final List<UUID> productIds = request.getMenuProductRequests().stream()
                .map(MenuProductRequest::getProductId)
                .collect(Collectors.toList());
        final Integer productSize = productRepository.countByIdIn(productIds);

        if (productSize != productIds.size()) {
            throw new IllegalArgumentException();
        }
    }
}
