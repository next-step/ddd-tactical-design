package kitchenpos.menu.domain.menugroup;

import com.google.common.base.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.support.vo.Name;

@Table(name = "menu_group_new")
@Entity
public class MenuGroupNew {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private Name name;

    protected MenuGroupNew() {
    }


    private MenuGroupNew(final UUID id, final Name name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroupNew create(final Name name) {
        return new MenuGroupNew(UUID.randomUUID(), name);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MenuGroupNew that = (MenuGroupNew) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }
}
