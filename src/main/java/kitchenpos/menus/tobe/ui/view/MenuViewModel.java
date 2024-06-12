package kitchenpos.menus.tobe.ui.view;

import java.util.ArrayList;
import java.util.List;
import kitchenpos.menus.tobe.query.result.MenuQueryResult;
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
            menu.getPrice().longValue() + "원",
            MenuGroupViewModel.from(menu.getMenuGroup()),
            menu.isDisplayed(),
            menu.getMenuProducts().stream().map(MenuProductViewModel::from).toList()
        );
    }

    public static MenuViewModel from(MenuQueryResult result) {
        List<MenuProductViewModel> menuProductViewModels = new ArrayList<>();
        menuProductViewModels.add(MenuProductViewModel.from(result));
        return new MenuViewModel(
            result.getMenuId(),
           result.getMenuName(),
            result.getMenuPrice().longValue() + "원",
            MenuGroupViewModel.from(result),
            result.isMenuDisplayed(),
            menuProductViewModels
        );
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public MenuGroupViewModel getMenuGroupViewModel() {
        return menuGroupViewModel;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    public List<MenuProductViewModel> getMenuProductViewModels() {
        return menuProductViewModels;
    }

    public void addMenuProductViewModel(MenuProductViewModel menuProductViewModel) {
        this.menuProductViewModels.add(menuProductViewModel);
    }
}
