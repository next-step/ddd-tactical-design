package kitchenpos.products.tobe.application;

import kitchenpos.products.dao.ProductDao;
import kitchenpos.products.model.ProductData;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.List;

public class ProductApplication {
    private final ProductDao productDao;

    public ProductApplication (final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Product RegisterNewProduct(String name, BigDecimal price){
        Product product = new Product(name, price);

        ProductData productData = new ProductData();
        productData.setId(product.getId());
        productData.setName(product.getName());
        productData.setPrice(product.getPrice());

        productDao.save(productData);

        return product;
    }

    public List<ProductData> productList() {
        return productDao.findAll();
    }
}
