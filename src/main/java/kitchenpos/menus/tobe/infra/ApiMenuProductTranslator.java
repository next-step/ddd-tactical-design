package kitchenpos.menus.tobe.infra;

import kitchenpos.common.infra.Profanities;
import kitchenpos.menus.tobe.domain.menuproducts.*;
import kitchenpos.menus.tobe.dto.FilteredProductRequest;
import kitchenpos.menus.tobe.dto.MenuProductRequest;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.dto.ProductResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ApiMenuProductTranslator implements MenuProductTranslator {
    private final Profanities profanities;
    private final RestTemplate restTemplate;
    private final MenuProductService menuProductService;

    public ApiMenuProductTranslator(final Profanities profanities, final RestTemplateBuilder restTemplateBuilder, final MenuProductService menuProductService) {
        this.profanities = profanities;
        this.restTemplate = restTemplateBuilder.build();
        this.menuProductService = menuProductService;
    }

    @Override
    public MenuProducts getMenuProducts(final List<MenuProductRequest> menuProductRequests) {
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
        return new MenuProducts(menuProducts);
    }

    private List<Product> requestProducts(final List<UUID> productIds) {
        return Arrays.stream(restTemplate.postForObject("http://localhost:8080/api/tobe/products/filtered",
                new FilteredProductRequest(productIds),
                ProductResponse[].class)
        ).map(this::response2Product).collect(Collectors.toList());
    }

    private Product requestProduct(final UUID productId) {
        return response2Product(restTemplate.getForObject("http://localhost:8080/api/tobe/products/" + productId, ProductResponse.class));
    }

    private Product response2Product(final ProductResponse response) {
        return new Product(
                response.getId(),
                new ProductName(response.getName(), profanities),
                new ProductPrice(response.getPrice()));
    }
}
