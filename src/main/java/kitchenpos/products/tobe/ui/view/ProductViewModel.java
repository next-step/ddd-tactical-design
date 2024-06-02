package kitchenpos.products.tobe.ui.view;

import java.math.BigDecimal;
import kitchenpos.products.tobe.domain.entity.Product;

public class ProductViewModel {
    private static final String KOREA_CURRENCY = "원";
    private final String id;
    private final String name;
    private final String price;


    private ProductViewModel(String id, String name, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductViewModel from(Product product) {
        return new ProductViewModel(product.getId().toString(), product.getName(), createPrice(product.getPrice()));
    }

    private static String createPrice(BigDecimal price) {
        return price.longValueExact() + KOREA_CURRENCY;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ProductViewModel{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", price='" + price + '\'' +
            '}';
    }
}
