package kitchenpos.products.tobe;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


public class ProductPrices {

    private final List<ProductPrice> productPrices;

    public ProductPrices(List<ProductPrice> productPrices) {
        this.productPrices = productPrices;
    }

    public static ProductPrices from(List<Product> products) {
        return new ProductPrices(products.stream()
                .map(it -> new ProductPrice(it.id(), it.price().value()))
                .toList());
    }


    public Money getPrice(UUID productId, long quantity) {
        return productPrices.stream()
                .filter(it -> it.getProductId().equals(productId))
                .findFirst()
                .map(it -> new Money(it.getPrice()).multiply(quantity))
                .orElseThrow(NoSuchElementException::new);
    }
}
