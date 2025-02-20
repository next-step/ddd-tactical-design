package kitchenpos.product.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menu.domain.service.MenuUpdatePolicy;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.model.ProductVo;
import kitchenpos.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductPurgomalumClient purgomalumClient;
    private final MenuUpdatePolicy menuUpdatePolicy;
    private final ProductCreatePolicy productCreatePolicy;

    public ProductServiceImpl(
        final ProductRepository productRepository,
        final ProductPurgomalumClient purgomalumClient,
        final MenuUpdatePolicy menuUpdatePolicy,
        final ProductCreatePolicy productCreatePolicy
    ) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.menuUpdatePolicy = menuUpdatePolicy;
        this.productCreatePolicy = productCreatePolicy;
    }

    @Override
    public ProductVo.ProductInfo create(final ProductVo.Create request) {
        final BigDecimal price = productCreatePolicy.validatePrice(request.price());
        final String name = productCreatePolicy.validateName(request.name(), purgomalumClient);

        return ProductVo.ProductInfo.fromEntity(
            productRepository.save(new Product(UUID.randomUUID(), name, price))
        );
    }

    @Override
    public ProductVo.ProductInfo changePrice(final ProductVo.Update request) {
        final BigDecimal price = productCreatePolicy.validatePrice(request.price());
        final Product product = productRepository.findById(request.productId())
            .orElseThrow(NoSuchElementException::new);

        product.update(price);

        menuUpdatePolicy.hideMenu(request.productId());

        return ProductVo.ProductInfo.fromEntity(product);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductVo.ProductInfo> findAll() {
        return productRepository.findAll()
            .stream()
            .map(ProductVo.ProductInfo::fromEntity)
            .toList();
    }
}
