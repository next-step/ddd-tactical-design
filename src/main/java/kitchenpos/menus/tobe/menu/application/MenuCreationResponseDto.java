package kitchenpos.menus.tobe.menu.application;

public class MenuCreationResponseDto {
    private Long id;

    public MenuCreationResponseDto(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
