package kitchenpos.menu.tobe.domain.vo;

import kitchenpos.common.name.NameFactory;
import kitchenpos.common.profanity.FakeProfanityDetectService;
import kitchenpos.common.profanity.domain.ProfanityDetectService;

public class MenuGroupFixture {

    private static final ProfanityDetectService profanityDetectService = new FakeProfanityDetectService();

    private static final NameFactory nameFactory = new NameFactory(profanityDetectService);

    public static MenuGroup menuGroupVo() {
        return new MenuGroup(nameFactory.create("두마리메뉴"));
    }
}
