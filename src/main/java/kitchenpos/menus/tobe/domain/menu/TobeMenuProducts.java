package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Embeddable
public class TobeMenuProducts {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<TobeMenuProduct> tobeMenuProducts = new ArrayList<>();

    public TobeMenuProducts() {
    }

    public TobeMenuProducts(final List<TobeMenuProduct> tobeMenuProducts) {
        if (Objects.isNull(tobeMenuProducts) || tobeMenuProducts.isEmpty()) {
            throw new IllegalArgumentException("메뉴의 상품은 비어있을 수 없습니다.");
        }

        this.tobeMenuProducts.addAll(tobeMenuProducts);
    }

    public TobeMenuProductPrice sum() {
        return tobeMenuProducts.stream()
                               .map(TobeMenuProduct::getPrice)
                               .reduce(TobeMenuProductPrice.zero(), TobeMenuProductPrice::sum);
    }

    public Stream<TobeMenuProduct> stream() {
        return this.tobeMenuProducts.stream();
    }

    public List<TobeMenuProduct> getTobeMenuProducts() {
        return tobeMenuProducts;
    }
}
