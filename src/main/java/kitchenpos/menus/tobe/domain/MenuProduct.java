package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.vo.Count;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MenuProduct {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Embedded
    private Count count;

    protected MenuProduct() {
    }

    public MenuProduct(Long productId, int count) {
        this.productId = productId;
        this.count = new Count(count);
    }

    public void changeCount(int count) {
        this.count = new Count(count);
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Count getCount() {
        return count;
    }
}
