package kitchenpos.menus.infrastructure;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JdbcTemplateMenuRepository implements MenuRepository {

    private final MenuDao menuDao;
    private final MenuProductDao menuProductDao;

    public JdbcTemplateMenuRepository(final MenuDao menuDao,
                                      final MenuProductDao menuProductDao) {
        this.menuDao = menuDao;
        this.menuProductDao = menuProductDao;
    }

    /**
     * Menu 의 식별자가 UUID 로서, 애플리케이션 레벨에서 미리 정의되었다 가정한다.
     * 반면에 MenuProducts 의 경우 Auto Increment 통해 정의된다 가정하기에 select 를 통해 조회 후 사용한다.
     * @param menu
     * @return
     */
    @Transactional
    @Override
    public Menu save(final Menu menu) {
        menuDao.save(menu);
        menuProductDao.saveAll(menu.menuProducts());
        final List<MenuProduct> menuProducts = menuProductDao.findAllByMenuId(menu.getIdValue());
        return menuDao.findAllById(menu.getIdValue(), menuProducts);
    }

    @Override
    public Optional<Menu> findById(final UUID id) {
        final List<MenuProduct> menuProducts = menuProductDao.findAllByMenuId(id);
        return Optional.ofNullable(menuDao.findById(id, menuProducts));
    }

    @Override
    public List<Menu> findAll() {
        final List<MenuProduct> menuProducts = menuProductDao.findAll();
        final List<UUID> menuIds = getMenuIds(menuProducts);
        return menuDao.findAllByIds(menuIds, menuProducts);
    }

    @Override
    public List<Menu> findAllByIdIn(final List<UUID> ids) {
        final List<MenuProduct> menuProducts = menuProductDao.findAllByMenuIds(ids);
        final List<UUID> menuIds = getMenuIds(menuProducts);
        return menuDao.findAllByIds(menuIds, menuProducts);
    }

    @Override
    public List<Menu> findAllByProductId(final Long productId) {
        final List<MenuProduct> menuProducts = menuProductDao.findAllBySeq(productId);
        final List<UUID> menuIds = getMenuIds(menuProducts);
        return menuDao.findAllByIds(menuIds, menuProducts);
    }

    private List<UUID> getMenuIds(final List<MenuProduct> menuProducts) {
        return menuProducts.stream().map(MenuProduct::menuIdValue).toList();
    }
}
