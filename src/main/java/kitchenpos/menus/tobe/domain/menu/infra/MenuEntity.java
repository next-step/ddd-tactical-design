package kitchenpos.menus.tobe.domain.menu.infra;

import kitchenpos.common.Name;
import kitchenpos.common.PositiveNumber;
import kitchenpos.common.Price;
import kitchenpos.menus.model.Menu;
import kitchenpos.menus.tobe.domain.menu.vo.MenuProducts;
import kitchenpos.menus.tobe.domain.menu.vo.MenuVO;

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
    @AttributeOverride(name = "number", column = @Column(name = "menuGroupId"))
    private PositiveNumber menuGroupId;

    @OneToMany(mappedBy = "menu", cascade = ALL, orphanRemoval = true)
    private List<MenuProductEntity> menuProducts = new ArrayList<>();

    protected MenuEntity() {}

    public MenuEntity (MenuVO menuVO){
        this.price = new Price(menuVO.getPrice());
        this.name = new Name(menuVO.getName());
        this.menuGroupId = new PositiveNumber(menuVO.getMenuGroupiId());
        this.menuProducts = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price.valueOf();
    }

    public String getName() {
        return name.valueOf();
    }

    public Long getMenuGroupId() {
        return menuGroupId.valueOf();
    }

    public List<MenuProductEntity> getMenuProducts() {
        return new ArrayList<>(menuProducts);
    }

    public void addMenuProducts (MenuProducts menuProducts){
        menuProducts.list()
            .stream()
            .forEach(vo -> {
                this.menuProducts.add(new MenuProductEntity(vo));
            });
    }

}
