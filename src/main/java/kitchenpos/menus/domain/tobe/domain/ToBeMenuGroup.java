package kitchenpos.menus.domain.tobe.domain;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tobe_menu_group")
@Entity
public class ToBeMenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    public ToBeMenuGroup() {
    }

    public ToBeMenuGroup(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("메뉴 그룹 이름이 입력되야 합니다.");
        }
        this.id = UUID.randomUUID();
        this.name = name;
    }
}
