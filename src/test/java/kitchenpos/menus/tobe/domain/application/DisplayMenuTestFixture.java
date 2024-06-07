package kitchenpos.menus.tobe.domain.application;


import kitchenpos.menus.tobe.domain.repository.MenuRepository;

public class DisplayMenuTestFixture extends DefaultDisplayMenu{

    public DisplayMenuTestFixture(MenuRepository menuRepository) {
        super(menuRepository);
    }
}
