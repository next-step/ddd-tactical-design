package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.ui.ProductForm;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity
public class TobeProduct {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public TobeProduct() {
    }

    public TobeProduct(UUID id, String name, BigDecimal price) {
        validationName(name);
        validationPrice(price);

        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static TobeProduct of(ProductForm productForm) {
        return new TobeProduct(
                UUID.randomUUID(),
                productForm.getName(),
                productForm.getPrice()
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void changePrice(BigDecimal price) {
        validationPrice(price);
        this.price = price;
    }

    private void validationName(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException();
        }
    }

    private void validationPrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public ProductForm toProductForm() {
        final ProductForm productForm = new ProductForm();
        productForm.setId(this.id);
        productForm.setName(this.name);
        productForm.setPrice(this.price);
        return productForm;
    }
}
