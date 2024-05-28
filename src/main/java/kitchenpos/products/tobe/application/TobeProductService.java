package kitchenpos.products.tobe.application;

import kitchenpos.menus.tobe.application.ProductEventHandler;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.dto.request.ProductCreateRequest;
import kitchenpos.products.tobe.dto.response.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TobeProductService {
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final ProductEventHandler productEventHandler;

    public TobeProductService(
            final ProductRepository productRepository,
            final PurgomalumClient purgomalumClient,
            final ProductEventHandler productEventHandler
    ) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.productEventHandler = productEventHandler;
    }

    /**
     * 상품을 등록합니다.
     *
     * @param request the request
     * @return the product response
     */
    @Transactional
    public ProductResponse create(final ProductCreateRequest request) {

        final ProductPrice price = ProductPrice.of(request.price());
        final String name = request.name();

        if (purgomalumClient.containsProfanity(name))
            throw new IllegalArgumentException("비속어가 포함된 상품명은 등록할 수 없습니다.");

        final Product product = Product.create(name, price);
        final Product saved = productRepository.save(product);

        return ProductResponse.of(
                saved.getId(),
                saved.getName(),
                saved.getPrice()
        );
    }

    /**
     * 상품의 가격을 변경합니다.
     *
     * @param productId 변경할 상품의 식별자
     * @param price     변경할 가격
     * @return the 변경된 ProductResponse 타입의 상품 결과
     */
    @Transactional
    public ProductResponse changePrice(final UUID productId, final BigDecimal price) {

        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다."));

        product.changePrice(price);
        productEventHandler.productPriceChangeEvent(productId);

        return ProductResponse.of(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }

    /**
     * 상품 목록을 조회합니다.
     *
     * @return the 조회된 ProductResponse 타입의 상품 결과 목록
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAllProductResponse();
    }
}
