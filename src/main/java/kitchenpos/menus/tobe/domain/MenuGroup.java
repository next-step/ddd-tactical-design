package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.ProfanityFilteredName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private ProfanityFilteredName profanityFilteredName;

    protected MenuGroup() {
    }

    protected MenuGroup(final ProfanityFilteredName profanityFilteredName) {
        this.id = UUID.randomUUID();
        this.profanityFilteredName = profanityFilteredName;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return profanityFilteredName.getValue();
    }
}
