package kitchenpos.menu.tobe.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String menuName;

    @Column(name = "price", nullable = false)
    private long menuPrice;

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

    public Menu(String menuName, long menuPrice, MenuGroup menuGroup, boolean menuDisplayStatus, MenuProducts menuProducts) {
        this.id = UUID.randomUUID();
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuGroup = menuGroup;
        this.menuDisplayStatus = menuDisplayStatus;
        this.menuProducts = menuProducts;
    }

    public UUID getId() {
        return id;
    }

    public String getMenuName() {
        return menuName;
    }

    public long getMenuPrice() {
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
}
