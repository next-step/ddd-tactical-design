package kitchenpos.menu.application.menu;

import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import java.util.stream.Collectors;
import kitchenpos.menu.application.menu.port.in.MenuDTO;
import kitchenpos.menu.application.menu.port.in.MenuRegistrationCommand;
import kitchenpos.menu.application.menu.port.in.MenuRegistrationUseCase;
import kitchenpos.menu.application.menu.port.out.MenuRepository;
import kitchenpos.menu.application.menu.port.out.ProductFindPort;
import kitchenpos.menu.application.menugroup.port.out.MenuGroupRepository;
import kitchenpos.menu.domain.menu.MenuName;
import kitchenpos.menu.domain.menu.MenuNameFactory;
import kitchenpos.menu.domain.menu.MenuNew;
import kitchenpos.menu.domain.menu.MenuProductNew;
import kitchenpos.menu.domain.menu.MenuProducts;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.transaction.annotation.Transactional;

public class DefaultMenuRegistrationUseCase implements MenuRegistrationUseCase {

    private final MenuRepository repository;
    private final MenuGroupRepository menuGroupRepository;
    private final MenuNameFactory menuNameFactory;
    private final ProductFindPort productFindPort;


    public DefaultMenuRegistrationUseCase(final MenuRepository repository,
        final MenuGroupRepository menuGroupRepository,
        final MenuNameFactory menuNameFactory,
        final ProductFindPort productFindPort) {

        this.repository = repository;
        this.menuGroupRepository = menuGroupRepository;
        this.menuNameFactory = menuNameFactory;
        this.productFindPort = productFindPort;
    }

    @Transactional
    @Override
    public MenuDTO register(final MenuRegistrationCommand command) {
        checkNotNull(command, "command");

        final MenuName menuName = menuNameFactory.create(command.getNameCandidate());

        final MenuNew menuNew = repository.save(
            MenuNew.create(menuName, command.getPrice(), create(command),
                menuGroupRepository.findById(command.getMenuGroupId())
                    .orElseThrow(() -> new IllegalArgumentException("menuGroup is not exist"))));

        return new MenuDTO(menuNew);
    }

    private MenuProducts create(final MenuRegistrationCommand command) {
        return MenuProducts.create(productFindPort.find(
                command.getProductIdAndQuantities()
                    .stream()
                    .map(Pair::getLeft)
                    .collect(Collectors.toUnmodifiableList()))
            .getValues()
            .stream()
            .map(product -> MenuProductNew.create(product.getId(), product.getPrice(),
                command.getProductQuantity(product.getId())))
            .collect(Collectors.toUnmodifiableList()));
    }
}
