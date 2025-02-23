package kitchenpos.menus.infrastructure;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.vo.MenuId;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Component
public class JdbcTemplateMenuProductDao implements MenuProductDao {

    private static final String SEQ = "seq";
    private static final String MENU_ID = "menu_id";
    private static final String PRODUCT_ID = "product_id";
    private static final String QUANTITY = "quantity";
    private static final String PRODUCT_PRICE = "product_price";
    private static final String MENU_PRODUCT_TABLE_NAME = "menu_product";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert menuProductJdbcInsert;

    public JdbcTemplateMenuProductDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.menuProductJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(MENU_PRODUCT_TABLE_NAME)
                .usingGeneratedKeyColumns(SEQ);
    }

    @Transactional
    @Override
    public void saveAll(final List<MenuProduct> menuProducts) {
        final List<MapSqlParameterSource> mapSqlParameterSources = menuProducts.stream()
                .map(menuProduct -> new MapSqlParameterSource()
                        .addValue(MENU_ID, menuProduct.menuIdValue())
                        .addValue(PRODUCT_ID, menuProduct.productId())
                        .addValue(QUANTITY, menuProduct.quantityValue())
                        .addValue(PRODUCT_PRICE, menuProduct.priceValue())
                ).toList();
        final int[] results = menuProductJdbcInsert.executeBatch(mapSqlParameterSources.toArray(new MapSqlParameterSource[0]));
        if (results.length != menuProducts.size()) {
            throw new IllegalArgumentException("메뉴 상품 저장에 실패했습니다.");
        }
    }

    @Override
    public List<MenuProduct> findAllByMenuId(final UUID menuId) {
        final String sql = "SELECT seq, menu_id, product_id, quantity, product_price FROM menu_product WHERE menu_id = :menu_id";
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("menu_id", menuId);
        return jdbcTemplate.query(sql, parameterSource, (resultSet, rowNumber) -> toMenuProduct(resultSet));
    }

    @Override
    public List<MenuProduct> findAll() {
        final String sql = "SELECT seq, menu_id, product_id, quantity, product_price FROM menu_product";
        return jdbcTemplate.query(sql, (resultSet, rowNumber) -> toMenuProduct(resultSet));
    }

    @Override
    public List<MenuProduct> findAllByMenuIds(final List<UUID> ids) {
        final String sql = "SELECT seq, menu_id, product_id, quantity, product_price FROM menu_product WHERE menu_id IN (:ids)";
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("ids", ids);
        return jdbcTemplate.query(sql, parameterSource, (resultSet, rowNumber) -> toMenuProduct(resultSet));
    }

    @Override
    public List<MenuProduct> findAllBySeq(final long seq) {
        final String sql = "SELECT seq, menu_id, product_id, quantity, product_price FROM menu_product WHERE product_id = :product_id";
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("product_id", seq);
        return jdbcTemplate.query(sql, parameterSource, (resultSet, rowNumber) -> toMenuProduct(resultSet));
    }

    private MenuProduct toMenuProduct(final ResultSet resultSet) throws SQLException {
        return new MenuProduct(
                resultSet.getLong(SEQ),
                resultSet.getLong(PRODUCT_PRICE),
                resultSet.getLong(QUANTITY),
                new MenuId(resultSet.getString(MENU_ID)),
                resultSet.getLong(PRODUCT_ID)
        );
    }
}
