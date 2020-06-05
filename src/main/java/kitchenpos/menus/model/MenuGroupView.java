package kitchenpos.menus.model;

public class MenuGroupView {

    private Long id;
    private String name;

    private MenuGroupView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }


    public static final class MenuGroupViewBuilder {

        private Long id;
        private String name;

        private MenuGroupViewBuilder() {
        }

        public static MenuGroupViewBuilder builder() {
            return new MenuGroupViewBuilder();
        }

        public MenuGroupViewBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public MenuGroupViewBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public MenuGroupView build() {
            MenuGroupView menuGroupView = new MenuGroupView();
            menuGroupView.setId(id);
            menuGroupView.setName(name);
            return menuGroupView;
        }
    }
}
