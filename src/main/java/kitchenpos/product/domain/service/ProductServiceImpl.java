package kitchenpos.product.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.model.ProductNameValidator;
import kitchenpos.product.domain.model.ProductVo;
import kitchenpos.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final ProductPurgomalumClient purgomalumClient;

    public ProductServiceImpl(
        final ProductRepository productRepository,
        final MenuRepository menuRepository,
        final ProductPurgomalumClient purgomalumClient
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public ProductVo.ProductInfo create(final ProductVo.Create request) {
        final BigDecimal price = request.price();
        final String name = new ProductNameValidator(request.name(), purgomalumClient).name();

        final Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName(name);
        product.setPrice(price);

        return ProductVo.ProductInfo.fromEntity(productRepository.save(product));
    }

    @Override
    public ProductVo.ProductInfo changePrice(final ProductVo.Update request) {
        final BigDecimal price = request.price();
        final Product product = productRepository.findById(request.productId())
            .orElseThrow(NoSuchElementException::new);

        product.setPrice(price);
        final List<Menu> menus = menuRepository.findAllByProductId(request.productId());
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                    menuProduct.getProduct()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
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
