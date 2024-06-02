package kitchenpos.menu.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.exception.IllegalPriceException;

import java.util.Objects;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName menuName;

    @Column(name = "price", nullable = false)
    private Long menuPrice;

    @ManyToOne
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean menuDisplayStatus;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {

    }

    public Menu(MenuName menuName, Long menuPrice, MenuGroup menuGroup, boolean menuDisplayStatus, MenuProducts menuProducts) {
        this.id = UUID.randomUUID();
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuGroup = menuGroup;
        this.menuDisplayStatus = menuDisplayStatus;
        this.menuProducts = menuProducts;
        validatePrice(menuPrice);
    }

    private static void validatePrice(Long menuPrice) {
        if (Objects.isNull(menuPrice) || menuPrice < 0) {
            throw new IllegalPriceException("가격은 0원 미만일 수 없습니다. ", menuPrice);
        }
    }

    public UUID getId() {
        return id;
    }

    public String getMenuName() {
        return menuName.getName();
    }

    public Long getMenuPrice() {
        return menuPrice;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public boolean isMenuDisplayStatus() {
        return menuDisplayStatus;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
