package kitchenpos.menu.tobe.infra.jpa;

import kitchenpos.common.name.NameFactory;
import kitchenpos.menu.tobe.domain.entity.MenuGroup;
import kitchenpos.util.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class MenuGroupEntityConverter {

    private MenuGroupEntityConverter() {
    }

    @Component
    public static class MenuGroupToMenuGroupEntityConverter
        implements Converter<MenuGroup, MenuGroupEntity> {

        @Override
        public MenuGroupEntity convert(final MenuGroup source) {
            final MenuGroupEntity menuGroupEntity = new MenuGroupEntity();
            menuGroupEntity.id = source.id;
            menuGroupEntity.name = source.name.value;
            return menuGroupEntity;
        }
    }

    @Component
    public static class MenuGroupEntityToMenuGroupConverter
        implements Converter<MenuGroupEntity, MenuGroup> {

        private final NameFactory nameFactory;

        @Autowired
        public MenuGroupEntityToMenuGroupConverter(final NameFactory nameFactory) {
            this.nameFactory = nameFactory;
        }

        @Override
        public MenuGroup convert(final MenuGroupEntity source) {
            return new MenuGroup(
                source.id,
                this.nameFactory.create(source.name)
            );
        }
    }
}
