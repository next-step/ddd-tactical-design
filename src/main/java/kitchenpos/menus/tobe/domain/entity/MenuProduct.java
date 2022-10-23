package kitchenpos.menus.tobe.domain.entity;

import kitchenpos.menus.dto.MenuProductRequest;
import kitchenpos.products.tobe.domain.entity.Product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "product_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private Product product;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Transient
    private UUID productId;

    public MenuProduct() {
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(final Long seq) {
        this.seq = seq;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(final long quantity) {
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(final UUID productId) {
        this.productId = productId;
    }

    public static List<MenuProduct> createMenuProductList(List<MenuProductRequest> menuProductRequestList, List<Product> productList) {
        List<MenuProduct> menuProductList = new ArrayList<>();
        for (final MenuProductRequest menuProductRequest : menuProductRequestList) {
            final Product product = productList.stream()
                    .filter(v -> v.getId().getId().equals(menuProductRequest.getProductId()))
                    .findFirst().orElseThrow(NoSuchElementException::new);
            final MenuProduct menuProduct = create(menuProductRequest, product);
            menuProductList.add(menuProduct);
        }
        return menuProductList;
    }

    public static MenuProduct create(MenuProductRequest menuProductRequest, Product product) {
        final long quantity = menuProductRequest.getQuantity();
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }
        final MenuProduct menuProduct = new MenuProduct();
        menuProduct.setProduct(product);
        menuProduct.setQuantity(quantity);
        return menuProduct;
    }
}
