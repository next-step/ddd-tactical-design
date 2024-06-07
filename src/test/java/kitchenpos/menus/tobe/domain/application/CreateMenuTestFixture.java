package kitchenpos.menus.tobe.domain.application;


import kitchenpos.common.purgomalum.PurgomalumClient;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.products.tobe.domain.repository.ProductRepository;

public class CreateMenuTestFixture extends DefaultCreateMenu{

    public CreateMenuTestFixture(MenuRepository menuRepository, ProductRepository productRepository, MenuGroupRepository menuGroupRepository, PurgomalumClient purgomalumClient) {
        super(menuRepository, productRepository, menuGroupRepository, purgomalumClient);
    }
}
