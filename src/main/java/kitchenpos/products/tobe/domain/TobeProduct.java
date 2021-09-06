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

    @Embedded
    private DisplayedName name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public TobeProduct() {
    }

    public TobeProduct(UUID id, DisplayedName name, BigDecimal price) {
        validationPrice(price);

        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static TobeProduct of(ProductForm productForm) {
        return new TobeProduct(
                UUID.randomUUID(),
                new DisplayedName(productForm.getName()),
                productForm.getPrice()
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void changePrice(BigDecimal price) {
        validationPrice(price);
        this.price = price;
    }

    private void validationPrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public ProductForm toProductForm() {
        final ProductForm productForm = new ProductForm();
        productForm.setId(this.id);
        productForm.setName(this.name.getName());
        productForm.setPrice(this.price);
        return productForm;
    }
}
