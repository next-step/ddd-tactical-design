package kitchenpos.menu.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kitchenpos.product.domain.model.ProductSummary;

@Entity
public class MenuSummary {

    @Id
    @Column(name = "id")
    private UUID id;
    private String menuName;
    private BigDecimal price;
    private boolean isDisplayed;
    private UUID menuGroupId;
    private String menuGroupName;

    @OneToMany
    @JoinColumn(name = "menu_summary_id")
    private List<ProductSummary> productSummaries = new ArrayList<>();

    public MenuSummary(String menuName, BigDecimal price, boolean isDisplayed, UUID menuGroupId,
                       String menuGroupName, List<ProductSummary> productSummaries) {
        this.id = UUID.randomUUID();
        this.menuName = menuName;
        this.price = price;
        this.isDisplayed = isDisplayed;
        this.menuGroupId = menuGroupId;
        this.menuGroupName = menuGroupName;
        this.productSummaries = productSummaries;
    }

    protected MenuSummary() {
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


