package kitchenpos.menu.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.product.domain.model.ProductSummary;

public class MenuSummaryEvent {

    private UUID id;
    private String menuName;
    private BigDecimal price;
    private boolean isDisplayed;
    private UUID menuGroupId;
    private String menuGroupName;
    private List<ProductSummary> productSummaries;

    private MenuSummaryEvent(UUID id, String menuName, BigDecimal price, boolean isDisplayed, UUID menuGroupId,
                             String menuGroupName, List<ProductSummary> productSummaries) {
        this.id = id;
        this.menuName = menuName;
        this.price = price;
        this.isDisplayed = isDisplayed;
        this.menuGroupId = menuGroupId;
        this.menuGroupName = menuGroupName;
        this.productSummaries = productSummaries;
    }

    public static MenuSummaryEvent from(Menu menu) {
        return new MenuSummaryEvent(
                menu.getId(),
                menu.getInnerName(),
                menu.getInnerPrice(),
                menu.isDisplayed(),
                menu.getMenuGroupId(),
                menu.getMenuGroup().getName(),
                menu.getMenuProducts().stream()
                        .map(mp -> new ProductSummary(mp.getProductId(), mp.getProduct().getInnerName(),
                                mp.getInnerQuantity()))
                        .toList()
        );
    }

    public UUID getId() {
        return id;
    }

    public String getMenuName() {
        return menuName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public String getMenuGroupName() {
        return menuGroupName;
    }

    public List<ProductSummary> getProductSummaries() {
        return productSummaries;
    }
}
