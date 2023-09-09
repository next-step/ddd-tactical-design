package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.*;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.ToBeProduct;
import kitchenpos.products.tobe.domain.ToBeProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ToBeMenuService {
    private final ToBeMenuRepository menuRepository;
    private final ToBeMenuGroupRepository menuGroupRepository;
    private final ToBeProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;

    public ToBeMenuService(
        final ToBeMenuRepository menuRepository,
        final ToBeMenuGroupRepository menuGroupRepository,
        final ToBeProductRepository productRepository,
        final PurgomalumClient purgomalumClient
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public ToBeMenu create(final ToBeMenu request) {
        final BigDecimal price = request.getPrice();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final ToBeMenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);
        final List<ToBeMenuProduct> menuProductRequests = request.getMenuProducts();
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<ToBeProduct> products = productRepository.findAllByIdIn(
            menuProductRequests.stream()
                .map(ToBeMenuProduct::getProductId)
                .collect(Collectors.toList())
        );
        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<ToBeMenuProduct> menuProducts = new ArrayList<>();
        BigDecimal sum = BigDecimal.ZERO;
        for (final ToBeMenuProduct menuProductRequest : menuProductRequests) {
            final long quantity = menuProductRequest.getQuantity();
            if (quantity < 0) {
                throw new IllegalArgumentException();
            }
            final ToBeProduct product = productRepository.findById(menuProductRequest.getProductId())
                .orElseThrow(NoSuchElementException::new);
            sum = sum.add(
                product.getPrice()
                    .multiply(BigDecimal.valueOf(quantity))
            );
            final ToBeMenuProduct menuProduct = new ToBeMenuProduct(product,quantity);
            menuProducts.add(menuProduct);
        }
        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
        final String name = request.getName();
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        final ToBeMenu menu = new ToBeMenu(name,price.longValue(),menuGroup,request.isDisplayed(),purgomalumClient,menuProducts);
        return menuRepository.save(menu);
    }

    @Transactional
    public ToBeMenu changePrice(final UUID menuId, final ToBeMenu request) {
        final BigDecimal price = request.getPrice();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final ToBeMenu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = BigDecimal.ZERO;
        for (final ToBeMenuProduct menuProduct : menu.getMenuProducts()) {
            sum = sum.add(
                menuProduct.getProduct()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
        menu.changePrice(price);
        return menu;
    }

    @Transactional
    public ToBeMenu display(final UUID menuId) {
        final ToBeMenu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = BigDecimal.ZERO;
        for (final ToBeMenuProduct menuProduct : menu.getMenuProducts()) {
            sum = sum.add(
                menuProduct.getProduct()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        if (menu.getPrice().compareTo(sum) > 0) {
            throw new IllegalStateException();
        }
        menu.changeDisplayed(true);
        return menu;
    }

    @Transactional
    public ToBeMenu hide(final UUID menuId) {
        final ToBeMenu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.changeDisplayed(false);
        return menu;
    }

    @Transactional(readOnly = true)
    public List<ToBeMenu> findAll() {
        return menuRepository.findAll();
    }
}
