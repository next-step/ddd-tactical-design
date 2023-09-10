package kitchenpos.menus.domain.tobe.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import kitchenpos.products.domain.tobe.domain.Price;

@Table(name = "tobe_menu")
@Entity
public class ToBeMenu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private Price price;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private ToBeMenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<ToBeMenuProduct> menuProducts;

    protected ToBeMenu() {
    }

    public ToBeMenu(String name, BigDecimal price, ToBeMenuGroup menuGroup, boolean displayed,
        boolean containsProfanity, List<ToBeMenuProduct> menuProducts) {

        validationOfProfanity(containsProfanity);
        validationOfMenuGroup(menuGroup);
        validationOfMenuProduct(menuProducts);
        validationOfPrice(price, menuProducts);

        this.id = UUID.randomUUID();
        this.name = Name.of(name);
        this.price = Price.of(price);
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public void changePrice(BigDecimal price) {
        validationOfPrice(price);
        this.price = Price.of(price);
    }

    public void display() {
        validationOfPrice();
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public Price getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    private void validationOfPrice(BigDecimal price, List<ToBeMenuProduct> menuProducts) {
        validationOfPrice(Price.of(price), getSumOfProductPrice(menuProducts));
    }

    private void validationOfPrice(BigDecimal price) {
        validationOfPrice(Price.of(price), getSumOfProductPrice(menuProducts));
    }

    private void validationOfPrice() {
        validationOfPrice(price, getSumOfProductPrice(menuProducts));
    }

    private void validationOfPrice(Price price, Price sumOfProducts) {
        if (price.isGreaterThan(sumOfProducts)) {
            throw new IllegalArgumentException("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.");
        }
    }

    private Price getSumOfProductPrice(List<ToBeMenuProduct> menuProducts) {
        return menuProducts.stream()
            .map(it -> it.getProductPrice().multiply(it.getQuantity()))
            .reduce(Price.of(BigDecimal.ZERO), Price::add);
    }

    private void validationOfMenuProduct(List<ToBeMenuProduct> menuProducts) {
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("상품이 없으면 등록할 수 없다.");
        }
    }

    private void validationOfMenuGroup(ToBeMenuGroup menuGroup) {
        if (menuGroup == null) {
            throw new IllegalArgumentException("메뉴는 특정 메뉴 그룹에 속해야 한다.");
        }
    }

    private void validationOfProfanity(boolean containsProfanity) {
        if (containsProfanity) {
            throw new IllegalArgumentException("상품 이름에 비속어가 포함되어 있습니다.");
        }
    }
}
