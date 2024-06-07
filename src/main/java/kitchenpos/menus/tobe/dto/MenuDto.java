package kitchenpos.menus.tobe.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import kitchenpos.menus.tobe.application.query.result.MenuQueryResult;

public class MenuDto {
    private String menuId;
    private String menuName;
    private BigDecimal menuPrice;
    private boolean isMenuDisplayed;
    private String menuGroupId;
    private String menuGroupName;
    private ArrayList<MenuProductDto> menuProducts = new ArrayList<>();

    private MenuDto(String menuId, String menuName, BigDecimal menuPrice, boolean isMenuDisplayed, String menuGroupId,
                    String menuGroupName) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.isMenuDisplayed = isMenuDisplayed;
        this.menuGroupId = menuGroupId;
        this.menuGroupName = menuGroupName;
    }

    public static MenuDto from(MenuQueryResult menuQueryResult) {
        MenuProductDto menuProductDto = MenuProductDto.from(menuQueryResult);
        MenuDto menuDto = new MenuDto(
            menuQueryResult.getMenuId(), menuQueryResult.getMenuName(),
            menuQueryResult.getMenuPrice(),
            menuQueryResult.isMenuDisplayed(),
            menuQueryResult.getMenuGroupId(),
            menuQueryResult.getMenuGroupName()
        );
        menuDto.addMenuProduct(menuProductDto);
        return menuDto;
    }

    public void addMenuProduct(MenuProductDto menuProductDto) {
        this.menuProducts.add(menuProductDto);
    }

    public String getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public BigDecimal getMenuPrice() {
        return menuPrice;
    }

    public boolean isMenuDisplayed() {
        return isMenuDisplayed;
    }

    public String getMenuGroupId() {
        return menuGroupId;
    }

    public String getMenuGroupName() {
        return menuGroupName;
    }

    public List<MenuProductDto> getMenuProducts() {
        return menuProducts;
    }
}
