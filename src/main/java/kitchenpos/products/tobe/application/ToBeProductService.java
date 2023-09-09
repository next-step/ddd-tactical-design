package kitchenpos.products.tobe.application;

import kitchenpos.menus.tobe.domain.ToBeMenu;
import kitchenpos.menus.tobe.domain.ToBeMenuProduct;
import kitchenpos.menus.tobe.domain.ToBeMenuRepository;
import kitchenpos.products.tobe.domain.ToBeProduct;
import kitchenpos.products.tobe.domain.ToBeProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class ToBeProductService {
    private final ToBeProductRepository toBeProductRepository;
    private final ToBeMenuRepository toBeMenuRepository;
    private final PurgomalumClient purgomalumClient;

    public ToBeProductService(
            ToBeProductRepository toBeProductRepository,
            ToBeMenuRepository menuRepository,
            PurgomalumClient purgomalumClient
    ) {
        this.toBeProductRepository = toBeProductRepository;
        this.toBeMenuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public ToBeProduct create(final ToBeProduct request) {
        final BigDecimal price = request.getPrice();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final String name = request.getName();
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        final ToBeProduct product = new ToBeProduct(name,price,this.purgomalumClient);

        return toBeProductRepository.save(product);
    }

    @Transactional
    public ToBeProduct changePrice(final UUID productId, final ToBeProduct request) {
        final BigDecimal price = request.getPrice();

        final ToBeProduct product = toBeProductRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);

        product.changePrice(price);
        final List<ToBeMenu> menus = toBeMenuRepository.findAllByProductId(productId);
        for (final ToBeMenu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final ToBeMenuProduct menuProduct : menu.getMenuProducts()) {
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
    public List<ToBeProduct> findAll() {
        return toBeProductRepository.findAll();
    }
}
