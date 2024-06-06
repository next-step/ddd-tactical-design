package kitchenpos.menus.domain.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.shared.domain.Profanities;

import java.util.List;
import java.util.UUID;

@Table(name = "tobe_menu")
@Entity
public class TobeMenu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

    @Embedded
    private Price price;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private TobeMenuProducts menuProducts;

    @Transient
    private UUID menuGroupId;

    private TobeMenu(String name, int price, Profanities profanities, UUID menuGroupId, List<TobeMenuProduct> tobeMenuProducts) {
        this.id = UUID.randomUUID();
        this.name = DisplayedName.of(name, profanities);
        this.price = Price.of(price);
        this.menuGroupId = menuGroupId;
        this.menuProducts = TobeMenuProducts.of(tobeMenuProducts);
    }

    public static TobeMenu of(String name, int price, Profanities profanities, UUID menuGroupId, List<TobeMenuProduct> tobeMenuProducts) {
        return new TobeMenu(name, price, profanities, menuGroupId, tobeMenuProducts);
    }

}
