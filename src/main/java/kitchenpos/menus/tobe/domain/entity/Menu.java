package kitchenpos.menus.tobe.domain.entity;

import kitchenpos.menus.dto.MenuCreateRequest;
import kitchenpos.menus.dto.MenuProductRequest;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.entity.Product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Table(name = "menu")
@Entity
public class Menu {
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
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    @Transient
    private UUID menuGroupId;

    public Menu() {
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

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(final MenuGroup menuGroup) {
        this.menuGroup = menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(final boolean displayed) {
        this.displayed = displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(final List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(final UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public static Menu createMenu(MenuCreateRequest menuCreateRequest, MenuGroup menuGroup, List<Product> productList, PurgomalumClient purgomalumClient) {
        List<MenuProduct> menuProductList = createMenuProduct(menuCreateRequest.getMenuProductRequestList(), productList);

        if (isInvalidPrice(menuCreateRequest.getPrice(), menuProductList, productList)) {
            throw new IllegalArgumentException("올바르지 않은 가격");
        }

        if (isInvalidName(menuCreateRequest.getName(), purgomalumClient)) {
            throw new IllegalArgumentException();
        }
        final Menu menu = new Menu();
        menu.setId(UUID.randomUUID());
        menu.setName(menuCreateRequest.getName());
        menu.setPrice(menuCreateRequest.getPrice());
        menu.setMenuGroup(menuGroup);
        menu.setDisplayed(menuCreateRequest.isDisplayed());
        menu.setMenuProducts(menuProductList);
        return menu;
    }

    public void changePrice(BigDecimal price) {
        List<Product> products = menuProducts.stream().map(v -> v.getProduct()).collect(Collectors.toList());
        if (isInvalidPrice(price, menuProducts, products)) {
            throw new IllegalArgumentException("유효하지 않은 가격");
        }
        this.price = price;
    }

    public void display() {
        List<Product> products = menuProducts.stream().map(v -> v.getProduct()).collect(Collectors.toList());
        if (isInvalidPrice(this.price, menuProducts, products)) {
            throw new IllegalStateException();
        }
        this.setDisplayed(true);
    }

    private static List<MenuProduct> createMenuProduct(List<MenuProductRequest> menuProductRequestList, List<Product> productList) {
        if (productList.size() != menuProductRequestList.size()) {
            throw new IllegalArgumentException();
        }

        return MenuProduct.createMenuProductList(menuProductRequestList, productList);
    }

    private static boolean isInvalidPrice(BigDecimal price, List<MenuProduct> menuProductList, List<Product> productList) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            return true;
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (MenuProduct menuProduct : menuProductList) {
            Product product = productList.stream()
                    .filter(v -> v.getId().getId().equals(menuProduct.getProduct().getId().getId())).findFirst().orElseThrow(NoSuchElementException::new);
            sum = sum.add(
                    product.getPrice()
                            .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }

        if (price.compareTo(sum) > 0) {
            return true;
        }

        return false;
    }

    public BigDecimal getMenuPrice() {
        BigDecimal sum = BigDecimal.ZERO;
        for (MenuProduct menuProduct : menuProducts) {
            sum = sum.add(
                    menuProduct.getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        return sum;
    }

    private static boolean isInvalidName(String name, PurgomalumClient purgomalumClient) {
        return Objects.isNull(name) || purgomalumClient.containsProfanity(name);
    }
}
