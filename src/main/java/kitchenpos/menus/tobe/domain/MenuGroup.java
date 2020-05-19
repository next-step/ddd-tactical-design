package kitchenpos.menus.tobe.domain;

public class MenuGroup {
    private Long id;
    private String name;

    public MenuGroup(String name) {
        this.name = name;
    }

    public MenuGroup(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }
}
