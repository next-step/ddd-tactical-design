package kitchenpos.menus.tobe.domain.application;


import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;

public class CreateMenuGroupTestFixture extends  DefaultCreateMenuGroup {

    public CreateMenuGroupTestFixture(MenuGroupRepository menuGroupRepository) {
        super(menuGroupRepository);
    }
}
