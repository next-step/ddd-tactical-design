package kitchenpos.menus.tobe.domain;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import kitchenpos.menus.tobe.domain.vo.Product;
import kitchenpos.menus.tobe.dto.ProductDTO;

public class MenuProductsService {

    private final ProductServerClient productServerClient;

    public MenuProductsService(ProductServerClient productServerClient) {
        this.productServerClient = productServerClient;
    }

    public void syncProduct(Menu menu) {
        MenuProducts menuProducts = menu.getMenuProducts();
        List<MenuProduct> requestMenuProducts = menuProducts.getValues();
        List<ProductDTO> products = findAllByIds(requestMenuProducts);
        if (products.size() != requestMenuProducts.size()) {
            throw new IllegalArgumentException();
        }

        Map<UUID, ProductDTO> mapper = convertToMap(products);
        for (MenuProduct menuProduct : requestMenuProducts) {
            UUID productId = menuProduct.getProductId();
            Product product = mapper.get(productId)
                    .toProduct();

            menuProduct.withProduct(product);
        }
    }

    private List<ProductDTO> findAllByIds(List<MenuProduct> menuProducts) {
        List<UUID> productIds = menuProducts.stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toList());

        return productServerClient.findAllByIds(productIds);
    }

    private Map<UUID, ProductDTO> convertToMap(List<ProductDTO> products) {
        return products.stream()
                .collect(collectingAndThen(
                        toMap(ProductDTO::getId, Function.identity()),
                        Collections::unmodifiableMap)
                );
    }
}
