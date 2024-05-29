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

    @Embedded
    private MenuProducts menuProducts;

    @Transient
    private UUID menuGroupId;

    protected Menu() {}

    public Menu(UUID id, String name, MenuNameValidationService menuNameValidationService,
                BigDecimal price, boolean displayed, List<MenuProduct> menuProducts,
                UUID menuGroupId) {
        this(
                id,
                new CleanName(name, menuNameValidationService),
                new Price(price),
                displayed,
                new MenuProducts(menuProducts),
                menuGroupId
        );
    }

    public Menu(UUID id, String name, BigDecimal price, boolean displayed,
                List<MenuProduct> menuProducts, UUID menuGroupId) {
        this (
                id,
                new CleanName(name),
                new Price(price),
                displayed,
                new MenuProducts(menuProducts),
                menuGroupId
        );
    }

    public Menu(UUID id, CleanName name, Price price, boolean displayed,
                MenuProducts menuProducts, UUID menuGroupId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
        this.menuProducts.checkNotLessThenMenuPrice(price.getPrice());
    }

    public void changePrice(BigDecimal changedMenuPrice) {
        menuProducts.checkNotLessThenMenuPrice(changedMenuPrice);
        price = new Price(changedMenuPrice);
    }

    public void display() {
        if (menuProducts.isLessThenMenuPrice(price.getPrice())) {
            hide();
            return;
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
