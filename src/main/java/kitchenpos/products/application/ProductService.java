package kitchenpos.products.application;

import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.values.Name;
import kitchenpos.common.values.Price;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static kitchenpos.common.exception.KitchenPosExceptionType.BAD_REQUEST;

@Service
public class ProductService {
    private final kitchenpos.products.tobe.domain.ProductRepository tobeProductRepository;

    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final PurgomalumClient purgomalumClient;

    public ProductService(
            final kitchenpos.products.tobe.domain.ProductRepository tobeProductRepository,
            final ProductRepository productRepository,
            final MenuRepository menuRepository,
            final PurgomalumClient purgomalumClient
    ) {
        this.tobeProductRepository = tobeProductRepository;
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public kitchenpos.products.tobe.domain.Product create(final Product request) {
        final Name name = new Name(request.getName());
        final Price price = new Price(request.getPrice());
        if (purgomalumClient.containsProfanity(name.getValue())) {
            throw new KitchenPosException("상품 이름에 비속어가 포함되어 있으므로", BAD_REQUEST);
        }
        final kitchenpos.products.tobe.domain.Product product = new kitchenpos.products.tobe.domain.Product(name, price);
        return tobeProductRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final Product request) {
        final BigDecimal price = request.getPrice();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.setPrice(price);
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
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
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
