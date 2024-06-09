package kitchenpos.menus.tobe.domain.application;


import kitchenpos.menus.tobe.domain.repository.MenuRepository;

public class CreateMenuGroupTestFixture extends  DefaultCreateMenuGroup {

    public CreateMenuGroupTestFixture(MenuRepository menuRepository) {
        super(menuRepository);
    }
}
