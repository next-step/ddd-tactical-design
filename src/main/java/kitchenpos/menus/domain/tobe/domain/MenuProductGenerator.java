package kitchenpos.menus.domain.tobe.domain;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import kitchenpos.common.DomainService;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.products.domain.tobe.domain.ToBeProduct;
import kitchenpos.products.domain.tobe.domain.ToBeProductRepository;

@DomainService
public class MenuProductGenerator {
    private final ToBeProductRepository productRepository;

    public MenuProductGenerator(ToBeProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ToBeMenuProduct> extractMenuProducts(final Menu request) {
        List<ToBeProduct> products = productFind(request.getMenuProducts());
        return products.stream()
            .map(item -> {
                MenuProduct matchingProduct = request.getMenuProducts()
                    .stream()
                    .filter(item::equals)
                    .findAny()
                    .orElseThrow(NoSuchElementException::new);
                return new ToBeMenuProduct(item, matchingProduct.getQuantity());
            })
            .collect(Collectors.toList());
    }

    private List<ToBeProduct> productFind(final List<MenuProduct> request) {
        List<ToBeProduct> products = productRepository.findAllByIdIn(request.stream()
            .map(MenuProduct::getProductId)
            .collect(Collectors.toList()));
        if (request.size() != products.size()) {
            throw new IllegalArgumentException("상품이 없으면 등록할 수 없다.");
        }
        return products;
    }
}
