package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import kitchenpos.products.tobe.domain.PurgomalumClient;

@Entity
public class Menu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName name = new MenuName();

    @Embedded
    private MenuPrice price = new MenuPrice();

    @Embedded
    private MenuProducts menuProducts = new MenuProducts();

    @Enumerated(EnumType.STRING)
    private DisplayStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private MenuGroup menuGroup = new MenuGroup();

    public Menu(String name, BigDecimal price, MenuGroup menuGroup, List<MenuProductRequest> menuProductRequests,
        PurgomalumClient purgomalumClient) {
        MenuProducts menuProducts = makeMenuProducts(menuProductRequests);

        if (menuPriceIsMoreThanProductsPrice(price, menuProducts.getTotalPrice())) {
            throw new IllegalArgumentException("메뉴의 가격이 상품들의 가격보다 높습니다");
        }

        this.id = UUID.randomUUID();
        this.name = new MenuName(name, purgomalumClient);
        this.price = new MenuPrice(price);
        this.menuGroup = menuGroup;
        this.menuProducts = menuProducts;
        this.status = DisplayStatus.DISPLAY;
    }


    private boolean menuPriceIsMoreThanProductsPrice(BigDecimal menuPrice, long productsTotalPrice) {
        return menuPrice.longValue() > productsTotalPrice;
    }

    private MenuProducts makeMenuProducts(List<MenuProductRequest> menuProductRequests) {
        List<MenuProduct> menuProductList = menuProductRequests.stream()
            .map(request -> new MenuProduct(this, request.getProductId(), request.getPrice(), request.getQuantity()))
            .collect(Collectors.toList());
        return new MenuProducts(menuProductList);
    }

    protected Menu() {
    }

    public void display() {
        this.status = DisplayStatus.DISPLAY;
    }

    public void hide() {
        this.status = DisplayStatus.HIDE;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.getMenuProducts();
    }

    public DisplayStatus getStatus() {
        return status;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public enum DisplayStatus {
        DISPLAY, HIDE
    }

    public static class MenuProductRequest {

        private final UUID productId;
        private final BigDecimal price;
        private final long quantity;

        public MenuProductRequest(UUID productId, BigDecimal price, long quantity) {
            this.productId = productId;
            this.price = price;
            this.quantity = quantity;
        }

        public UUID getProductId() {
            return productId;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public long getQuantity() {
            return quantity;
        }
    }
}
