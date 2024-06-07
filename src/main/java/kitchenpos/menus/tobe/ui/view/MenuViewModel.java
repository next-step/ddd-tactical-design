package kitchenpos.menus.tobe.ui.view;

import java.util.List;
import kitchenpos.menus.tobe.domain.entity.Menu;

public class MenuViewModel {
    private String id;
    private String name;
    private String price;
    private MenuGroupViewModel menuGroupViewModel;
    private boolean isDisplayed;
    private List<MenuProductViewModel> menuProductViewModels;

    private MenuViewModel(String id, String name, String price, MenuGroupViewModel menuGroupViewModel,
                         boolean isDisplayed,
                         List<MenuProductViewModel> menuProductViewModels) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupViewModel = menuGroupViewModel;
        this.isDisplayed = isDisplayed;
        this.menuProductViewModels = menuProductViewModels;
    }

    public static MenuViewModel from(Menu menu) {
        return new MenuViewModel(
            menu.getId().toString(),
            menu.getName(),
            String.valueOf(menu.getPrice().longValue()) + "원",
            null,
            menu.isDisplayed(),
            null
        );
    }
}
