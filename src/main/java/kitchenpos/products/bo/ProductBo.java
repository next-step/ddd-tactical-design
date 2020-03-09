package kitchenpos.products.bo;

import kitchenpos.products.model.ProductData;
import kitchenpos.products.tobe.application.ProductApplication;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductBo {
    private final ProductApplication productApplication;

    public ProductBo(final ProductApplication productApplication) {
        this.productApplication = productApplication;
    }

    public ProductData create(final ProductData productData) {
        return productApplication.registerNewProduct(productData.getName(), productData.getPrice());
    }

    public List<ProductData> list() {
        return productApplication.productList();
    }
}
