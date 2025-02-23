package kitchenpos.menus.tobe.infrastructure;

import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.vo.MenuGroupId;
import kitchenpos.menus.tobe.domain.vo.MenuGroupName;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JdbcTemplateMenuGroupRepository implements MenuGroupRepository {

    private static final String MENU_GROUP_TABLE_NAME = "menu_group";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert menuGroupJdbcInsert;

    public JdbcTemplateMenuGroupRepository(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.menuGroupJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(MENU_GROUP_TABLE_NAME);
    }

    @Override
    public MenuGroup save(final MenuGroup menuGroup) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(menuGroup);
        final KeyHolder keyHolder = menuGroupJdbcInsert.executeAndReturnKeyHolder(parameters);
        return select(keyHolder.getKeyAs(UUID.class));
    }

    @Override
    public Optional<MenuGroup> findById(final UUID id) {
        try {
            return Optional.of(select(id));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<MenuGroup> findAll() {
        final String sql = "SELECT id, name FROM menu_group";
        return jdbcTemplate.query(sql, (resultSet, rowNumber) -> toEntity(resultSet));
    }

    private MenuGroup select(final UUID id) {
        final String sql = "SELECT id, name FROM menu_group WHERE id = :id";
        final SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.queryForObject(sql, parameters, (resultSet, rowNumber) -> toEntity(resultSet));
    }

    private MenuGroup toEntity(final ResultSet resultSet) throws SQLException {
        return new MenuGroup(
                new MenuGroupId(resultSet.getString(ID_COLUMN)),
                new MenuGroupName(resultSet.getString(NAME_COLUMN))
        );
    }
}
