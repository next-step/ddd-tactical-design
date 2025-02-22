package kitchenpos.menu.adapter.out.persistance.entity;

import jakarta.persistence.*;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.shared.domain.Profanities;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class MenuEntity {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroupEntity menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProductEntity> menuProducts;

    @Transient
    private UUID menuGroupId;

    public MenuEntity() {
    }

    public static MenuEntity of(Menu menu, MenuGroupEntity menuGroupEntity) {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setId(menu.getId());
        menuEntity.setName(menu.getName());
        menuEntity.setPrice(menu.getPrice());
        menuEntity.setMenuGroupId(menu.getMenuGroupId());
        menuEntity.setDisplayed(menu.isDisplayed());
        menuEntity.setMenuGroup(menuGroupEntity);
        menuEntity.setMenuProducts(menu.getMenuProducts()
                .stream()
                .map(MenuProductEntity::of)
                .toList());
        return menuEntity;
    }

    public Menu toDomain(Profanities profanities) {
        return Menu.create(
                this.id,
                this.name,
                this.price,
                this.displayed,
                this.menuGroup.getId(),
                this.menuProducts
                        .stream()
                        .map(MenuProductEntity::toDomain)
                        .toList(),
                profanities
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public MenuGroupEntity getMenuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(final MenuGroupEntity menuGroup) {
        this.menuGroup = menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(final boolean displayed) {
        this.displayed = displayed;
    }

    public List<MenuProductEntity> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(final List<MenuProductEntity> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(final UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuEntity that = (MenuEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
