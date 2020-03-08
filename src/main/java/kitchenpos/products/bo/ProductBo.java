package kitchenpos.products.bo;

import kitchenpos.products.model.ProductData;
import kitchenpos.products.tobe.application.ProductApplication;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ProductBo {
    private final ProductApplication productApplication;

    public ProductBo(final ProductApplication productApplication) {
        this.productApplication = productApplication;
    }

    @Transactional
    public ProductData create(final ProductData productData) {

        Product newProduct = productApplication.RegisterNewProduct(productData.getName(), productData.getPrice());

        productData.setId(newProduct.getId());
        productData.setName(newProduct.getName());
        productData.setPrice(newProduct.getPrice());

        return productData;
    }

    public List<ProductData> list() {
        return productApplication.productList();
    }
}
