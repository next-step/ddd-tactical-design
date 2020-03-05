package kitchenpos.products.tobe.domain.product.domain;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Access(AccessType.FIELD)
public class ProductDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ProductInfo productInfo;

    public ProductDomain() {
    }

    public ProductDomain(Product product) {
        this.productInfo = new ProductInfo(product);
    }

    public Product toProduct() {
        return new Product(this.id, productInfo.getName(), productInfo.getPrice());
    }
}
