package kitchenpos.products.bo;

import kitchenpos.products.dao.ProductDao;
import kitchenpos.products.model.ProductData;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
public class ProductBo {
    private final ProductDao productDao;

    public ProductBo(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public ProductData create(final ProductData productData) {

        Product newProduct = new Product(productData.getName(), productData.getPrice());

        productData.setId(newProduct.getId());
        productData.setName(newProduct.getName());
        productData.setPrice(newProduct.getPrice());

        return productDao.save(productData);
    }

    public List<ProductData> list() {
        return productDao.findAll();
    }
}
