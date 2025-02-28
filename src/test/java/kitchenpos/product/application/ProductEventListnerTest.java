package kitchenpos.product.application;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.product.application.dto.ProductRequest;
import kitchenpos.product.application.facade.ProductFacade;
import kitchenpos.product.domain.model.ProductId;
import kitchenpos.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@RecordApplicationEvents
@Sql({"/db/data.sql"})
class ProductEventListenerTest {

    @Autowired
    private ProductFacade productFacade;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MenuRepository menuRepository;

    @DisplayName("상품 가격 변경시 - 메뉴 숨김처리 이벤트 검증")
    @Test
    void 가격비교_숨김처리() {
        var productId = UUID.fromString("3b528244-34f7-406b-bb7e-690912f66b10");

        var beforeProduct = productRepository.findByProductId(ProductId.of(productId))
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MENU.toString()));

        var request = new ProductRequest.UpdatePrice(productId, BigDecimal.valueOf(100));

        var menuId = UUID.fromString("f59b1e1c-b145-440a-aa6f-6095a0e2d63b");

        var beforeMenu = menuRepository.findByMenuId(MenuId.of(menuId))
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MENU.toString()));

        productFacade.changePrice(request);

        var afterProduct = productRepository.findByProductId(ProductId.of(productId))
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MENU.toString()));

        var afterMenu = menuRepository.findByMenuId(MenuId.of(menuId))
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MENU.toString()));

        assertAll(
            () -> assertNotEquals(beforeProduct.getPrice(), afterProduct.getPrice()),
            () -> assertTrue(beforeMenu.isDisplayed()),
            () -> assertFalse(afterMenu.isDisplayed()),
            () -> assertNotEquals(beforeMenu.isDisplayed(), afterMenu.isDisplayed())
        );
    }
}
