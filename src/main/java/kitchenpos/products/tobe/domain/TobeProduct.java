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
    private ProductName name;

    @Embedded
    private ProductPrice price;

    public TobeProduct() {
    }

    public TobeProduct(UUID id, ProductName name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static TobeProduct of(ProductForm productForm) {
        return new TobeProduct(
                UUID.randomUUID(),
                new ProductName(productForm.getName()),
                new ProductPrice(productForm.getPrice())
        );
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

    public void changePrice(BigDecimal price) {
        this.price.changePrice(price);
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
        productForm.setPrice(this.price.getPrice());
        return productForm;
    }
}
