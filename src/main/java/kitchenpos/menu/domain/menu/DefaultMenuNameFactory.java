package kitchenpos.menu.domain.menu;

import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import kitchenpos.menu.adapter.menu.out.MenuPurgomalumChecker;
import kitchenpos.menu.application.exception.ContainsProfanityException;
import kitchenpos.support.vo.Name;

public class DefaultMenuNameFactory implements MenuNameFactory {

    private final MenuPurgomalumChecker purgomalumChecker;

    public DefaultMenuNameFactory(final MenuPurgomalumChecker purgomalumChecker) {
        this.purgomalumChecker = purgomalumChecker;
    }

    @Override
    public MenuName create(final Name nameCandidate) {
        checkNotNull(nameCandidate, "nameCandidate");

        if (purgomalumChecker.containsProfanity(nameCandidate)) {
            throw new ContainsProfanityException(nameCandidate);
        }

        return MenuName.create(nameCandidate);
    }
}
