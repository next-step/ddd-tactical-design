package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProductService;
import kitchenpos.menus.tobe.domain.MenuProductTranslator;
import kitchenpos.menus.tobe.domain.ProductQuantity;
import kitchenpos.menus.tobe.dto.MenuProductRequest;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

// @Component
public class ServiceMenuProductTranslator implements MenuProductTranslator {
    private final ProductRepository productRepository;
    private final MenuProductService menuProductService;

    public ServiceMenuProductTranslator(final ProductRepository productRepository, final MenuProductService menuProductService) {
        this.productRepository = productRepository;
        this.menuProductService = menuProductService;
    }

    @Override
    public List<MenuProduct> getMenuProducts(final List<MenuProductRequest> menuProductRequests) {
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
        menuProductService.validateMenuProducts(products, menuProducts);
        return menuProducts;
    }
}
