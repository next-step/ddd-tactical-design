package kitchenpos.menu.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    @OneToMany(mappedBy = "menuSummary")
    private List<ProductSummary> productSummaries = new ArrayList<>();
}


