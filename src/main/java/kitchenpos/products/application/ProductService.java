package kitchenpos.products.application;

import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.values.Name;
import kitchenpos.common.values.Price;
import kitchenpos.products.dto.ChangePriceRequest;
import kitchenpos.products.dto.CreateReqeust;
import kitchenpos.products.event.ProductPriceChangeEvent;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static kitchenpos.common.exception.KitchenPosExceptionType.BAD_REQUEST;
import static kitchenpos.common.exception.KitchenPosExceptionType.NOT_FOUND;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final ApplicationEventPublisher publisher;

    public ProductService(
            final ProductRepository productRepository,
            final PurgomalumClient purgomalumClient,
            final ApplicationEventPublisher publisher) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.publisher = publisher;
    }

    @Transactional
    public Product create(final CreateReqeust request) {
        final Name name = new Name(request.getName());
        final Price price = new Price(request.getPrice());
        if (purgomalumClient.containsProfanity(name.getValue())) {
            throw new KitchenPosException("상품 이름에 비속어가 포함되어 있으므로", BAD_REQUEST);
        }
        final Product product = new Product(name, price);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ChangePriceRequest request) {
        final Price price = new Price(request.getPrice());
        final Product product = productRepository.findById(productId)
            .orElseThrow(() -> new KitchenPosException("요청하신 ID에 해당하는 상품을", NOT_FOUND));
        product.changePrice(price);
        publisher.publishEvent(new ProductPriceChangeEvent(product.getId()));
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
