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
    private CleanName name;

    @Embedded
    private Price price;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

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
                new CleanName(name, menuNameValidationService),
                new Price(price),
                displayed,
                menuGroup,
                menuGroupId,
                new MenuProducts(menuProducts)
        );
    }

    public Menu(UUID id, String name, BigDecimal price, MenuGroup menuGroup, UUID menuGroupId,
                boolean displayed, List<MenuProduct> menuProducts) {
        this (
                id,
                new CleanName(name),
                new Price(price),
                displayed,
                menuGroup,
                menuGroupId,
                new MenuProducts(menuProducts)
        );
    }

    public Menu(UUID id, CleanName name, Price price, boolean displayed, MenuGroup menuGroup,
                UUID menuGroupId, MenuProducts menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.displayed = displayed;
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

        displayed = true;
    }

    public void hide() {
        displayed = false;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }
}
