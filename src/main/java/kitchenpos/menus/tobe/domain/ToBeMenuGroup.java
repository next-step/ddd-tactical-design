package kitchenpos.menus.tobe.domain;

import kitchenpos.common.values.Name;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class ToBeMenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private Name name;

    protected ToBeMenuGroup() {
    }

}
