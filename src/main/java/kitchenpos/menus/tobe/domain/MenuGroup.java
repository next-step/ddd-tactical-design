package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuGroupName name;

    public MenuGroup() {
    }

    public MenuGroup(String name) {
        this.name = new MenuGroupName(name);
    }

    public MenuGroup(UUID id, String name) {
        validation(name);

        this.id = id;
        this.name = new MenuGroupName(name);
    }

    private void validation(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("메뉴그룹의 이름은 Null또는 공백이 될수 없습니다.");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

}
