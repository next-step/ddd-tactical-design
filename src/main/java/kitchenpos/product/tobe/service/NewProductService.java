package kitchenpos.product.tobe.service;

import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.product.tobe.domain.NewProduct;
import kitchenpos.product.tobe.domain.NewProductRepository;
import kitchenpos.profanity.domain.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class NewProductService {
    private final NewProductRepository newProductRepository;
    private final MenuRepository menuRepository;
    private final PurgomalumClient purgomalumClient;

    public NewProductService(
        final NewProductRepository newProductRepository,
        final MenuRepository menuRepository,
        final PurgomalumClient purgomalumClient
    ) {
        this.newProductRepository = newProductRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public NewProduct create(final NewProduct request) {
        final BigDecimal price = request.getPrice();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final String name = request.getName();
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        final NewProduct product = new NewProduct();
        product.setId(UUID.randomUUID());
        product.setName(name);
        product.setPrice(price);
        return newProductRepository.save(product);
    }

    @Transactional
    public NewProduct changePrice(final UUID productId, final NewProduct request) {
        final BigDecimal price = request.getPrice();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final NewProduct product = newProductRepository.findById(productId)
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
    public List<NewProduct> findAll() {
        return newProductRepository.findAll();
    }
}
