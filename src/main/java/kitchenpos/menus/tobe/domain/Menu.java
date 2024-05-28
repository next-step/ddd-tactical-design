package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

    @Embedded
    private Price price;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Transient
    private UUID menuGroupId;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {}

    public Menu(UUID id, String name, MenuNameValidationService menuNameValidationService,
                   BigDecimal price, MenuGroup menuGroup, UUID menuGroupId, boolean displayed,
                   List<MenuProduct> menuProducts) {
        this(
                id,
                new DisplayedName(displayed, name, menuNameValidationService),
                new Price(price),
                menuGroup,
                menuGroupId,
                new MenuProducts(menuProducts)
        );
    }

    public Menu(UUID id, String name, BigDecimal price, MenuGroup menuGroup, UUID menuGroupId,
                boolean displayed, List<MenuProduct> menuProducts) {
        this (
                id,
                new DisplayedName(displayed, new CleanName(name)),
                new Price(price),
                menuGroup,
                menuGroupId,
                new MenuProducts(menuProducts)
        );
    }

    public Menu(UUID id, DisplayedName name, Price price, MenuGroup menuGroup,
                UUID menuGroupId, MenuProducts menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
        this.menuProducts.checkNotLessThenMenuPrice(price.getPrice());
    }

    public void changePrice(BigDecimal changedMenuPrice) {
        menuProducts.checkNotLessThenMenuPrice(changedMenuPrice);
        price = new Price(changedMenuPrice);
    }

    public void display() {
        int result = price.getPrice().compareTo(menuProducts.calculateTotalPrice());
        if (result == 1) {
            hide();
            return ;
        }

        name.display();
    }

    public void hide() {
        name.hide();
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public boolean isDisplayed() {
        return name.isDisplayed();
    }
}
