package kitchenpos.products.tobe.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;


@Service
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    /**
     * 사용자가 Product 를 등록한다.
     * @param productName 상품의 이름
     * @param price 상품의 가격, notnull 이며 0 보다 커야한다.
     * @return 등록한 Product
     */
    @Transactional
    public Product create(final String productName, final BigDecimal price) {
        final Product product = new Product(productName, price);

        return productDao.save(product);
    }

    public List<Product> list() {
        return productDao.findAll();
    }
}
