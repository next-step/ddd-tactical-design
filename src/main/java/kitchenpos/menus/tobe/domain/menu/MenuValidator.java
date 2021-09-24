package kitchenpos.menus.tobe.domain.menu;

@FunctionalInterface
public interface MenuValidator {

    void validate(final Menu menu);
}
