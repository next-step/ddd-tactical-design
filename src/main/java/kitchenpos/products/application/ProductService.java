package kitchenpos.products.application;

import kitchenpos.common.domain.Purgomalum;
import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.values.Name;
import kitchenpos.common.values.Price;
import kitchenpos.products.dto.ChangePriceRequest;
import kitchenpos.products.dto.CreateReqeust;
import kitchenpos.products.dto.ProductDto;
import kitchenpos.products.event.ProductPriceChangeEvent;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static kitchenpos.common.exception.KitchenPosExceptionType.NOT_FOUND;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final Purgomalum purgomalum;
    private final ApplicationEventPublisher publisher;

    public ProductService(
            final ProductRepository productRepository,
            final Purgomalum purgomalum,
            final ApplicationEventPublisher publisher) {
        this.productRepository = productRepository;
        this.purgomalum = purgomalum;
        this.publisher = publisher;
    }

    @Transactional
    public ProductDto create(final CreateReqeust request) {
        final Name name = new Name(request.getName(), purgomalum);
        final Price price = new Price(request.getPrice());
        final Product product = new Product(name, price);
        Product savedProduct = productRepository.save(product);
        return ProductDto.from(savedProduct);
    }

    @Transactional
    public ProductDto changePrice(final UUID productId, final ChangePriceRequest request) {
        final Price price = new Price(request.getPrice());
        final Product product = productRepository.findById(productId)
            .orElseThrow(() -> new KitchenPosException("요청하신 ID에 해당하는 상품을", NOT_FOUND));
        product.changePrice(price);
        publisher.publishEvent(new ProductPriceChangeEvent(product.getId()));
        return ProductDto.from(product);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
