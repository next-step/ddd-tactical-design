package kitchenpos.menus.tobe.query.result;

import java.math.BigDecimal;

public class MenuQueryResult {
    private String menuId;
    private String menuName;
    private BigDecimal menuPrice;
    private boolean isMenuDisplayed;
    private String menuGroupId;
    private String menuGroupName;
    private Long productQuantity;
    private String productId;
    private String productName;
    private BigDecimal productPrice;

    public MenuQueryResult(String menuId, String menuName, BigDecimal menuPrice, boolean isMenuDisplayed,
                           String menuGroupId, String menuGroupName, Long productQuantity, String productId,
                           String productName, BigDecimal productPrice) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.isMenuDisplayed = isMenuDisplayed;
        this.menuGroupId = menuGroupId;
        this.menuGroupName = menuGroupName;
        this.productQuantity = productQuantity;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
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

    public Long getProductQuantity() {
        return productQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

}
