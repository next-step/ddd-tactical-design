package kitchenpos.menus.domain;

import org.springframework.stereotype.Component;

@Component
public class MenuPricePolicy {
    public void follow(Price menuPrice, Price menuProductsTotalPrice) {
        if (menuPrice.isSmallerOrEqualTo(menuProductsTotalPrice)) {
            return;
        }
        throw new IllegalArgumentException(String.format(
                "메뉴의 가격은 메뉴에 속한 상품들의 가격 총합보다 작거나 같아야 합니다. 현재 값: 메뉴가격 %s, 메뉴에 속한 상품들 가격 총합 %s",
                menuPrice.stringValue(), menuProductsTotalPrice.stringValue()));
    }
}
