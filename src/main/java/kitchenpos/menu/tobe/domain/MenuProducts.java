package kitchenpos.menu.tobe.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> value;

    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 값으로 메뉴항목 컬렉션을 생성할 수 없습니다.");
        }

        this.value = new ArrayList<>(value);
    }

    public MenuPrice sumOfMenuProductPrice() {
        return value.stream()
            .map(MenuProduct::calculatePrice)
            .reduce(MenuPrice.ZERO, MenuPrice::add);
    }

    public void changeMenuProductPrice(UUID productId, MenuPrice price) { // TODO(경록) : test code 작성 예정
        MenuProduct menuProduct = value.stream()
            .filter(it -> it.equalsToProductId(productId))
            .findAny()
            .orElseThrow(NoSuchElementException::new);

        menuProduct.changePrice(price);
    }

    public List<MenuProduct> getValue() {
        return Collections.unmodifiableList(value);
    }
}
