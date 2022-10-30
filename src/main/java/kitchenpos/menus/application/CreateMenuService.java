package kitchenpos.menus.application;

import kitchenpos.common.domain.infra.PurgomalumValidator;
import kitchenpos.common.domain.vo.Name;
import kitchenpos.common.domain.vo.Price;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.tobe.domain.entity.MenuProduct;
import kitchenpos.menus.tobe.domain.entity.IncludedProduct;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.domain.repository.IncludedProductRepository;
import kitchenpos.menus.tobe.domain.vo.MenuProductQuantity;
import kitchenpos.menus.ui.dto.CreateMenuRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateMenuService {
    private final MenuGroupRepository menuGroupRepository;
    private final PurgomalumValidator purgomalumValidator;
    private final IncludedProductRepository includedProductRepository;
    private final MenuRepository menuRepository;

    public CreateMenuService(
        MenuGroupRepository menuGroupRepository,
        PurgomalumValidator purgomalumValidator,
        IncludedProductRepository includedProductRepository,
        MenuRepository menuRepository
    ) {
        this.menuGroupRepository = menuGroupRepository;
        this.purgomalumValidator = purgomalumValidator;
        this.includedProductRepository = includedProductRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public Menu create(final CreateMenuRequest request) {
        final MenuGroup menuGroup = menuGroupRepository.findById(request.menuGroupId)
                .orElseThrow(IllegalArgumentException::new);

        Name name = new Name(request.name, purgomalumValidator);
        final Price price = new Price(request.price);
        final Menu menu = new Menu(price, name, request.isDisplayed, menuGroup);

        for (final CreateMenuRequest.CreateMenuProductRequest menuProductRequest : request.createMenuProductRequests) {
            final IncludedProduct includedProduct = includedProductRepository.findById(menuProductRequest.productId)
                    .orElseThrow(IllegalArgumentException::new);
            final MenuProductQuantity quantity = new MenuProductQuantity(menuProductRequest.quantity);
            final MenuProduct menuProduct = new MenuProduct(
                    quantity, includedProduct
            );
            menu.registerMenuProduct(menuProduct);
        }

        return menuRepository.save(menu);
    }
}
