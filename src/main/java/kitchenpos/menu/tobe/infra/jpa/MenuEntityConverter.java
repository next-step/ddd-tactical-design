package kitchenpos.menu.tobe.infra.jpa;

import java.util.List;
import kitchenpos.common.name.NameFactory;
import kitchenpos.common.price.Price;
import kitchenpos.menu.tobe.domain.entity.Menu;
import kitchenpos.menu.tobe.infra.jpa.MenuGroupEntityConverter.MenuGroupEntityToMenuGroupConverter;
import kitchenpos.menu.tobe.infra.jpa.MenuProductEntityConverter.MenuProductEntityToMenuProductConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class MenuEntityConverter {

    private MenuEntityConverter() {
    }

    @Component
    public static class MenuToMenuEntityConverter {

        public MenuEntity convert(final Menu menu) {
            final MenuEntity menuEntity = new MenuEntity();
            menuEntity.id = menu.id;
            menuEntity.name = menu.name.value;
            menuEntity.menuGroupId = menu.menuGroup.id;
            menuEntity.displayed = menu.displayed();
            menuEntity.price = menu.price().value;
            return menuEntity;
        }
    }

    @Component
    public static class MenuEntityToMenuConverter {

        private final MenuGroupEntityToMenuGroupConverter menuGroupEntityToMenuGroupConverter;

        private final MenuProductEntityToMenuProductConverter menuProductEntityToMenuProductConverter;

        private final NameFactory nameFactory;

        @Autowired
        public MenuEntityToMenuConverter(
            final MenuGroupEntityToMenuGroupConverter menuGroupEntityToMenuGroupConverter,
            final MenuProductEntityToMenuProductConverter menuProductEntityToMenuProductConverter,
            final NameFactory nameFactory
        ) {
            this.menuGroupEntityToMenuGroupConverter = menuGroupEntityToMenuGroupConverter;
            this.menuProductEntityToMenuProductConverter = menuProductEntityToMenuProductConverter;
            this.nameFactory = nameFactory;
        }

        public Menu convert(
            final MenuEntity menu,
            final MenuGroupEntity menuGroup,
            final List<MenuProductEntity> menuProducts
        ) {
            return new Menu(
                menu.id,
                this.nameFactory.create(menu.name),
                menu.displayed,
                new Price(menu.price),
                this.menuGroupEntityToMenuGroupConverter.convert(menuGroup),
                this.menuProductEntityToMenuProductConverter.convert(menuProducts)
            );
        }
    }
}
