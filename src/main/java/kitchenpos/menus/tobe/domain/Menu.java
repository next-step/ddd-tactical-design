package kitchenpos.menus.tobe.domain;

import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kitchenpos.common.domain.Name;
import kitchenpos.common.domain.Price;
import kitchenpos.products.infra.PurgomalumClient;

@Table(name = "menu")
@Entity
public class Menu {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private Name name;

    @Column(name = "price", nullable = false)
    private Price price;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_to_menu_group"))
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() { }

    private Menu(UUID id, Name name, Price price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        validPrice(price, menuProducts);
        
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public static Menu create(String name, PurgomalumClient purgomalumClient, Long price, MenuGroup menuGroup, boolean displayed, List<MenuProduct> menuProducts) {
        return new Menu(UUID.randomUUID(), new Name(name, purgomalumClient), new Price(price), menuGroup, displayed, MenuProducts.from(menuProducts));
    }

    private void validPrice(Price price, MenuProducts menuProducts) {
        if (price.isBiggerThen(menuProducts.getSumProductsPrice())) {
            throw new IllegalArgumentException("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 합니다.");
        }
    }

    public void modifyPrice(Long value) {
        price = new Price(value);

        if (price.isBiggerThen(menuProducts.getSumProductsPrice())) {
            hideMenu();
        }
    }

    public void displayMenu() {
        this.displayed = true;
    }

    public void hideMenu() {
        this.displayed = false;
    }
}
