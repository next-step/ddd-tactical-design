package kitchenpos.product.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.common.name.Name;
import kitchenpos.common.price.Price;
import kitchenpos.product.tobe.domain.entity.Product;
import kitchenpos.product.tobe.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product create(final Product request) {
        final Price price = request.price();
        if (Objects.isNull(price)) {
            throw new IllegalArgumentException();
        }
        final Name name = request.name;
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException();
        }
        final Product product = new Product(UUID.randomUUID(), name, price);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final Product request) {
        final Price price = request.price();
        if (Objects.isNull(price)) {
            throw new IllegalArgumentException();
        }
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.setPrice(price);
        // TODO: 메뉴 서비스에 상품 가격이 변경되었음을 알려 메뉴의 노출 여부가 함께 업데이트될 수 있도록 한다.
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
