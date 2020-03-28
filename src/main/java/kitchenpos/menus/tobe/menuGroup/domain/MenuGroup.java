package kitchenpos.menus.tobe.menuGroup.domain;

import org.apache.logging.log4j.util.Strings;

import javax.persistence.*;

@Entity
public class MenuGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuGroup() {
    }

    public MenuGroup(final String name) {
        if (Strings.isBlank(name)) {
            throw new IllegalArgumentException("메뉴그룹명은 빈 문자열일 수 없습니다.");
        }

        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
