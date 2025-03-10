package kitchenpos.menus.tobe.domain.menuproduct;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import kitchenpos.menus.tobe.domain.ProductPriceService;

@Embeddable
public class MenuProducts {

    public static final String ERROR_MESSAGE_EMPTY = "최소 1개 이상의 메뉴 상품이 포함되어야 합니다.";


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> value;

    public MenuProducts(List<MenuProduct> value) {
        validate(value);
        this.value = value;
    }

    protected MenuProducts() {

    }

    private static void validate(List<MenuProduct> value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_EMPTY);
        }
    }

    public List<MenuProduct> getValue() {
        return value;
    }

    public BigDecimal calculateTotalProductPrice(ProductPriceService productPriceService) {
        return getValue().stream()
            .map(x -> x.calculatePrice(productPriceService))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        MenuProducts that = (MenuProducts) object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
