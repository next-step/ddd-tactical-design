package kitchenpos.eatinorders.tobe.infra.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import kitchenpos.eatinorders.tobe.domain.model.Menu;
import kitchenpos.eatinorders.tobe.domain.repository.MenuRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMenuRepository implements MenuRepository {

    private static final String TABLE_NAME = "product";
    private static final String KEY_COLUMN_NAME = "id";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcMenuRepository(final DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(KEY_COLUMN_NAME);
    }

    @Override
    public Optional<Menu> findById(UUID id) {
        try {
            return Optional.of(select(id));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Menu> findAllByIdIn(List<UUID> ids) {
        final String sql = "SELECT id, displayedName, price, displayed FROM menu WHERE id IN (:ids)";
        return jdbcTemplate.query(sql, (resultSet, rowNumber) -> toEntity(resultSet));
    }

    private Menu select(final UUID id) {
        final String sql = "SELECT id, displayedName, price, displayed FROM menu WHERE id = (:id)";
        final SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.queryForObject(sql, parameters, (resultSet, rowNumber) -> toEntity(resultSet));
    }

    private Menu toEntity(final ResultSet resultSet) throws SQLException {
        final Menu entity = new Menu(UUID.fromString(resultSet.getString(KEY_COLUMN_NAME)),
                resultSet.getString("displayedName"),
                resultSet.getBigDecimal("price"),
                resultSet.getBoolean("displayed"));
        return entity;
    }
}
