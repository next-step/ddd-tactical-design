package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.ProductQuantity;
import kitchenpos.menus.tobe.dto.MenuProductRequest;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class ServiceMenuProductTranslator implements MenuProductTranslator {
    private final ProductRepository productRepository;

    public ServiceMenuProductTranslator(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public MenuProducts translateMenuProducts(final List<MenuProductRequest> menuProductRequests) {
        final List<Product> products = productRepository.findAllByIdIn(menuProductRequests.stream()
                .map(MenuProductRequest::getProductId)
                .collect(Collectors.toList()));
        final List<MenuProduct> menuProducts = menuProductRequests.stream()
                .map(menuProductRequest -> {
                    final Product product = productRepository.findById(menuProductRequest.getProductId())
                            .orElseThrow(NoSuchElementException::new);
                    final ProductQuantity productQuantity = new ProductQuantity(menuProductRequest.getQuantity());
                    return new MenuProduct(product, productQuantity);
                }).collect(Collectors.toList());
        return new MenuProducts(products, menuProducts);
    }
}
