package kitchenpos.menus.tobe.domain.menu.infra;

import kitchenpos.common.Name;
import kitchenpos.common.PositiveNumber;
import kitchenpos.common.Price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "menu")
public class MenuEntity {

    @Id
    @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "price"))
    private Price price;

    @Embedded
    private Name name;

    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "amount"))
    private Price amount;

    @Embedded
    @AttributeOverride(name = "number", column = @Column(name = "menuGroupId"))
    private PositiveNumber menuGroupId;

    @OneToMany(mappedBy = "menu", cascade = ALL, orphanRemoval = true)
    private List<MenuProductEntity> menuProductEntityList = new ArrayList<>();

    protected MenuEntity() {}

    public MenuEntity(Long id,
                      BigDecimal price,
                      String name,
                      Long menuGroupId,
                      List<MenuProductEntity> menuProductEntityList,
                      BigDecimal amount){
        this.id = id;
        this.price = new Price(price);
        this.name = new Name(name);
        this.menuGroupId = new PositiveNumber(menuGroupId);
        this.menuProductEntityList = new ArrayList<>(menuProductEntityList);
        this.amount = new Price(amount);
    }

    public List<MenuProductEntity> getMenuProducts (){
        return new ArrayList<>(menuProductEntityList);
    }

}
