package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProductService;
import kitchenpos.menus.tobe.domain.MenuProductTranslator;
import kitchenpos.menus.tobe.domain.ProductQuantity;
import kitchenpos.menus.tobe.dto.FilteredProductRequest;
import kitchenpos.menus.tobe.dto.MenuProductRequest;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ApiMenuProductTranslator implements MenuProductTranslator {
    private final RestTemplate restTemplate;
    private final MenuProductService menuProductService;

    public ApiMenuProductTranslator(final RestTemplateBuilder restTemplateBuilder, final MenuProductService menuProductService) {
        this.restTemplate = restTemplateBuilder.build();
        this.menuProductService = menuProductService;
    }

    @Override
    public List<MenuProduct> getMenuProducts(final List<MenuProductRequest> menuProductRequests) {
        final List<Product> products = requestProducts(menuProductRequests.stream()
                .map(MenuProductRequest::getProductId)
                .collect(Collectors.toList()));
        final List<MenuProduct> menuProducts = menuProductRequests.stream()
                .map(menuProductRequest -> {
                    final Product product = requestProduct(menuProductRequest.getProductId());
                    final ProductQuantity productQuantity = new ProductQuantity(menuProductRequest.getQuantity());
                    return new MenuProduct(product, productQuantity);
                }).collect(Collectors.toList());
        menuProductService.validateMenuProducts(products, menuProducts);
        return menuProducts;
    }

    private List<Product> requestProducts(final List<UUID> productIds) {
        return Arrays.asList(restTemplate.getForObject("http://localhost:8080/api/tobe/products/filtered", Product[].class, new FilteredProductRequest(productIds)));
    }

    private Product requestProduct(final UUID productId) {
        return restTemplate.getForObject("http://localhost:8080/api/tobe/products/" + "productId", Product.class);
    }
}
