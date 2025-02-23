package kitchenpos.menus.infrastructure;

import kitchenpos.menus.tobe.Menu;
import kitchenpos.menus.tobe.MenuProduct;
import kitchenpos.menus.tobe.MenuRepository;
import org.springframework.stereotype.Component;

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

    @Override
    public Menu save(final Menu menu) {
        menuDao.save(menu);
        menuProductDao.saveAll(menu.menuProducts());
        final List<MenuProduct> menuProducts = menuProductDao.findAllByMenuId(menu.getId());
        return menuDao.findAllById(menu.getId(), menuProducts);
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
        return menuProducts.stream().map(MenuProduct::menuId).toList();
    }
}
