package kitchenpos.menu.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.common.name.Name;
import kitchenpos.common.name.NameFactory;
import kitchenpos.common.price.Price;
import kitchenpos.menu.tobe.application.dto.ChangeMenuPriceCommand;
import kitchenpos.menu.tobe.application.dto.CreateMenuCommand;
import kitchenpos.menu.tobe.domain.entity.Menu;
import kitchenpos.menu.tobe.domain.entity.MenuGroup;
import kitchenpos.menu.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menu.tobe.domain.repository.MenuRepository;
import kitchenpos.menu.tobe.domain.vo.MenuProduct;
import kitchenpos.product.tobe.domain.entity.Product;
import kitchenpos.product.tobe.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    private final MenuGroupRepository menuGroupRepository;

    private final ProductRepository productRepository;

    private final NameFactory nameFactory;

    @Autowired
    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final ProductRepository productRepository,
        final NameFactory nameFactory
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.nameFactory = nameFactory;
    }

    @Transactional
    public Menu create(final CreateMenuCommand command) {
        final Price price = new Price(command.price);
        final MenuGroup menuGroup = menuGroupRepository.findById(command.menuGroupId)
            .orElseThrow(NoSuchElementException::new);
        final List<MenuProduct> menuProductRequests = command.menuProducts;
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Product> products = productRepository.findAllByIdIn(
            menuProductRequests.stream()
                .map(menuProduct -> menuProduct.productId)
                .collect(Collectors.toUnmodifiableList())
        );
        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }
        final Name name = this.nameFactory.create(command.name);
        final Menu menu = new Menu(
            UUID.randomUUID(),
            name,
            command.displayed,
            price,
            menuGroup,
            command.menuProducts
        );
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final ChangeMenuPriceCommand command) {
        final Price price = new Price(command.price);
        final Menu menu = menuRepository.findById(command.id)
            .orElseThrow(NoSuchElementException::new);
        menu.setPrice(price);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.display();
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
