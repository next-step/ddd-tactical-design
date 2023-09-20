package kitchenpos.products.application;

import kitchenpos.common.domain.PurgomalumClient;
import kitchenpos.products.shared.dto.request.ProductChangePriceRequest;
import kitchenpos.products.shared.dto.request.ProductCreateRequest;
import kitchenpos.products.shared.dto.response.ProductDto;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductMenuService;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.shared.util.ConvertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final ProductMenuService productMenuService;

    public ProductService(
            final ProductRepository productRepository,
            final PurgomalumClient purgomalumClient,
            final ProductMenuService productMenuService
    ) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.productMenuService = productMenuService;
    }

    @Transactional
    public ProductDto create(final ProductCreateRequest productCreateRequest) {
        Product product = Product.of(
                new ProductName(productCreateRequest.getName(), purgomalumClient),
                new ProductPrice(productCreateRequest.getPrice())
        );
        return ConvertUtil.convert(productRepository.save(product), ProductDto.class);
    }

    @Transactional
    public ProductDto changePrice(final UUID productId, final ProductChangePriceRequest productChangePriceRequest) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changeProductPrice(productChangePriceRequest.getPrice());
        productMenuService.validateMenuPrice(productId);
        return ConvertUtil.convert(product, ProductDto.class);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        return ConvertUtil.convertList(productRepository.findAll(), ProductDto.class);
    }
}
