package kitchenpos.menu.domain.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.entity.MenuGroup;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.menu.domain.model.MenuVo;
import kitchenpos.menu.domain.repository.MenuGroupRepository;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final MenuPurgomalumClient purgomalumClient;
    private final MenuCreatePolicy menuCreatePolicy;

    public MenuServiceImpl(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final ProductRepository productRepository,
        final MenuPurgomalumClient purgomalumClient,
        final MenuCreatePolicy menuCreatePolicy
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.menuCreatePolicy = menuCreatePolicy;
    }

    @Override
    public MenuVo.MenuInfo create(final MenuVo.Create request) {
        final BigDecimal price = menuCreatePolicy.validatePrice(request.price());
        final String name = menuCreatePolicy.validateMenuName(request.name(), purgomalumClient);

        final MenuGroup menuGroup = menuGroupRepository.findById(request.menuGroupId())
            .orElseThrow(NoSuchElementException::new);

        final List<MenuProduct> menuProductRequests = request.menuProducts();
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Product> products = productRepository.findAllByIdIn(
            menuProductRequests.stream()
                .map(MenuProduct::getProductId)
                .toList()
        );
        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<MenuProduct> menuProducts = new ArrayList<>();
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProductRequest : menuProductRequests) {
            final long quantity = menuProductRequest.getQuantity();
            if (quantity < 0) {
                throw new IllegalArgumentException();
            }
            final Product product = productRepository.findById(menuProductRequest.getProductId())
                .orElseThrow(NoSuchElementException::new);
            sum = sum.add(
                product.getPrice()
                    .multiply(BigDecimal.valueOf(quantity))
            );
            final MenuProduct menuProduct = new MenuProduct();
            menuProduct.setProduct(product);
            menuProduct.setQuantity(quantity);
            menuProducts.add(menuProduct);
        }
        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }

        return MenuVo.MenuInfo.fromEntity(
            menuRepository.save(
                new Menu(
                        UUID.randomUUID(),
                        name,
                        price,
                        menuGroup,
                        request.displayed(),
                        menuProducts)
            )
        );
    }

    @Override
    public MenuVo.MenuInfo changePrice(final MenuVo.Update request) {
        final BigDecimal price = menuCreatePolicy.validatePrice(request.price());

        final Menu menu = menuRepository.findById(request.menuId())
            .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
            sum = sum.add(
                menuProduct.getProduct()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
        menu.updatePrice(price);
        return MenuVo.MenuInfo.fromEntity(menu);
    }

    @Override
    public MenuVo.MenuInfo display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
            sum = sum.add(
                menuProduct.getProduct()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        if (menu.getPrice().compareTo(sum) > 0) {
            throw new IllegalStateException();
        }
        menu.updateDisplayed(true);
        return MenuVo.MenuInfo.fromEntity(menu);
    }

    @Override
    public MenuVo.MenuInfo hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.updateDisplayed(false);
        return MenuVo.MenuInfo.fromEntity(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuVo.MenuInfo> findAll() {
        return menuRepository.findAll()
            .stream()
            .map(MenuVo.MenuInfo::fromEntity)
            .toList();
    }
}
