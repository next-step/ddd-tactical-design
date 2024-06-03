package kitchenpos.menus.domain.tobe.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "tobe_menu_group")
@Entity
public class TobeMenuGroup {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    @Embedded
    private MenuGroupName name;

    private TobeMenuGroup() {
    }

    public TobeMenuGroup(UUID id, String name) {
        this.id = id;
        this.name = MenuGroupName.of(name);
    }

}