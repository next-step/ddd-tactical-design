package kitchenpos.product.tobe.infra.jpa;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "product")
@Entity
class ProductEntity {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    public UUID id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "price", nullable = false)
    public BigDecimal price;

    public ProductEntity() {
    }
}
