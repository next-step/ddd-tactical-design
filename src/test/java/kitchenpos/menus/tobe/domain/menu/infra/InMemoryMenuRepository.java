package kitchenpos.menus.tobe.domain.menu.infra;

import kitchenpos.menus.model.Menu;
import kitchenpos.menus.tobe.domain.menu.support.MenuBuilder;
import kitchenpos.menus.tobe.domain.menu.vo.MenuProductVO;
import kitchenpos.menus.tobe.domain.menu.vo.MenuProducts;

import java.util.*;

public class InMemoryMenuRepository implements MenuRepository {

    private Map<Long, MenuEntity> entities = new HashMap<>();

    @Override
    public MenuEntity save(MenuEntity menuEntity) {

        MenuProducts menuProducts = new MenuProducts();
        menuEntity.getMenuProducts()
            .stream()
            .forEach(menuProduct -> {
                menuProducts.add(
                    new MenuProductVO(menuProduct.getProductId(), menuProduct.getQuantity())
                );
            });

        MenuEntity saveMenuEntity = new MenuBuilder()
            .id(menuEntity.getId())
            .price(menuEntity.getPrice())
            .name(menuEntity.getName())
            .menuGroupid(menuEntity.getMenuGroupId())
            .menuProducts(menuProducts)
            .build();

        entities.put(menuEntity.getId(), menuEntity);

        return menuEntity;
    }

    @Override
    public Optional<MenuEntity> findById(Long id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public List<MenuEntity> findAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public boolean findByName(String name) {
        List<MenuEntity> menuEntities = this.findAll();
        for(MenuEntity menuEntity : menuEntities){
            if(name.equals(menuEntity.getName())){
                return true;
            }
        }
        return false;
    }
}
