package kitchenpos.menus.tobe.domain.menugroup;

import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "tobe_menu_group")
@Entity
public class TobeMenuGroup {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuGroupName name;

    private TobeMenuGroup() {
    }

    public TobeMenuGroup(UUID id, String name) {
        this.id = id;
        this.name = MenuGroupName.of(name);
    }

    public MenuGroupName getName() {
        return name;
    }
}
