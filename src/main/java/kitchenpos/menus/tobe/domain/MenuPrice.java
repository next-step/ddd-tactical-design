package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public class MenuPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
