package kitchenpos.menus.infrastructure;

import kitchenpos.menus.tobe.Menu;
import kitchenpos.menus.tobe.MenuProduct;
import kitchenpos.products.tobe.domain.vo.EmptyProfanities;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.stream.Collectors.groupingBy;

@Component
public class JdbcTemplateMenuDao implements MenuDao {

    private static final String MENU_TABLE_NAME = "menu";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String MENU_GROUP_ID = "menu_group_id";
    private static final String DISPLAYED = "displayed";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert menuJdbcInsert;

    public JdbcTemplateMenuDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.menuJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(MENU_TABLE_NAME);
    }

    @Override
    public void save(final Menu menu) {
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(menu);
        final int execute = menuJdbcInsert.execute(parameterSource);
        if (execute == 0) {
            throw new IllegalArgumentException("메뉴 저장에 실패했습니다.");
        }
    }

    @Override
    public Menu findById(final UUID id, final List<MenuProduct> menuProducts) {
        final Map<UUID, List<MenuProduct>> menuProductMap = menuProductMap(menuProducts);
        final String sql = "SELECT id, name, price, menu_group_id, displayed FROM menu WHERE id = :id";
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);
        return jdbcTemplate.queryForObject(sql, parameterSource, (resultSet, rowNumber) -> toEntity(resultSet, menuProductMap));
    }

    @Override
    public Menu findAllById(final UUID id, final List<MenuProduct> menuProducts) {
        final Map<UUID, List<MenuProduct>> menuProductMap = menuProductMap(menuProducts);

        final String sql = "SELECT id, name, price, menu_group_id, displayed FROM menu WHERE id = :id";
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);
        return jdbcTemplate.queryForObject(sql, parameterSource, (resultSet, rowNumber) -> toEntity(resultSet, menuProductMap));
    }

    @Override
    public List<Menu> findAllByIds(final List<UUID> ids, final List<MenuProduct> menuProducts) {
        final Map<UUID, List<MenuProduct>> menuProductMap = menuProductMap(menuProducts);
        final String sql = "SELECT id, name, price, menu_group_id, displayed FROM menu WHERE id IN (:ids)";
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("ids", ids);
        return jdbcTemplate.query(sql, parameterSource, (resultSet, rowNumber) -> toEntity(resultSet, menuProductMap));
    }

    private Menu toEntity(final ResultSet resultSet, final Map<UUID, List<MenuProduct>> menuProductMap) throws SQLException {
        return new Menu(
                UUID.fromString(resultSet.getString(ID)),
                resultSet.getString(NAME),
                new EmptyProfanities(),
                resultSet.getLong(PRICE),
                UUID.fromString(resultSet.getString(MENU_GROUP_ID)),
                menuProductMap.get(UUID.fromString(resultSet.getString(ID))),
                resultSet.getBoolean(DISPLAYED)
        );
    }

    private Map<UUID, List<MenuProduct>> menuProductMap(final List<MenuProduct> menuProducts) {
        return menuProducts.stream()
                .collect(groupingBy(MenuProduct::menuId));
    }
}
