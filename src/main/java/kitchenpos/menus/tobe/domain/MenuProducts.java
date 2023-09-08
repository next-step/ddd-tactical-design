package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Embeddable
public class MenuProducts {

    @OneToMany(mappedBy = "menu", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MenuProduct> menuProducts = new ArrayList<>();

    protected MenuProducts() { }

    public MenuProducts(List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("구성품은 비어있 을 수 없습니다");
        }
        this.menuProducts = menuProducts;
    }

    public BigDecimal getTotalPrice() {
        return menuProducts.stream()
                .map(it -> it.getProduct().getPrice().multiply(BigDecimal.valueOf(it.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<UUID> getProductIds() {
        return menuProducts.stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toList());
    }

    public int size() {
        return menuProducts.size();
    }
}
