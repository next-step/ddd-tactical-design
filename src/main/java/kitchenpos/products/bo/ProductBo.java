package kitchenpos.products.bo;

import kitchenpos.products.dao.ProductDao;
import kitchenpos.products.model.ProductData;
import kitchenpos.products.tobe.application.ProductApplication;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ProductBo {
    private final ProductDao productDao;
    private final ProductApplication productApplication;

    public ProductBo(final ProductDao productDao, ProductApplication productApplication) {
        this.productDao = productDao;
        this.productApplication = productApplication;
    }

    @Transactional
    public ProductData create(final ProductData productData) {

        Product newProduct = productApplication.RegisterNewProduct(productData.getName(), productData.getPrice());

        productData.setId(newProduct.getId());
        productData.setName(newProduct.getName());
        productData.setPrice(newProduct.getPrice());

        return productDao.save(productData);
    }

    public List<ProductData> list() {
        return productDao.findAll();
    }
}
