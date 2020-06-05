package kitchenpos.menus.tobe.infra;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.common.model.Price;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProductFactory;
import kitchenpos.menus.tobe.domain.menu.ProductQuantity;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class MenuProductFactoryAdapter implements MenuProductFactory {

    private final ProductRepository productRepository;

    public MenuProductFactoryAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<MenuProduct> getMenuProducts(List<ProductQuantity> productQuantities) {
        List<Long> ids = productQuantities.stream()
            .map(ProductQuantity::getProductId)
            .collect(Collectors.toList());

        List<Product> products = getProducts(productQuantities, ids);

        return productQuantities.stream()
            .map(productQuantity -> new MenuProduct(productQuantity,
                getPrice(products, productQuantity)))
            .collect(Collectors.toList());
    }

    private List<Product> getProducts(List<ProductQuantity> productQuantities, List<Long> ids) {
        List<Product> products = productRepository.findAllById(ids);
        if (productQuantities.size() != products.size()) {
            throw new IllegalArgumentException("not exist product");
        }
        return products;
    }

    private Price getPrice(List<Product> products, ProductQuantity productQuantity) {
        return products.stream().filter(
            product1 -> product1.getId() == productQuantity.getProductId()).findFirst()
            .orElseThrow(IllegalArgumentException::new)
            .getPrice();
    }
}
