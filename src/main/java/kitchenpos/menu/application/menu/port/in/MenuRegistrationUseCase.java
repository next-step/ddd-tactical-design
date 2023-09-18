package kitchenpos.menu.application.menu.port.in;

public interface MenuRegistrationUseCase {

    MenuDTO register(final MenuRegistrationCommand command);
}
