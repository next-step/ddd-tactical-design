package kitchenpos.menus.application;

import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.values.Quantity;
import kitchenpos.menus.dto.CreateMenuProductRequest;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.products.application.ProductValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static kitchenpos.common.exception.KitchenPosExceptionType.BAD_REQUEST;

@Service
public class MenuProductService {

    private final ProductValidator productValidator;

    public MenuProductService(final ProductValidator productValidator) {
        this.productValidator = productValidator;
    }

    public List<MenuProduct> create(final List<CreateMenuProductRequest> request) {
        if (Objects.isNull(request) || request.isEmpty()) {
            throw new KitchenPosException("메뉴 구성 상품이 없으므로", BAD_REQUEST);
        }

        List<UUID> productIds = request.stream()
                .map(CreateMenuProductRequest::getProductId)
                .collect(Collectors.toList());

        productValidator.isExistProducts(productIds);

        return request.stream()
                .map(e -> new MenuProduct(e.getProductId(), new Quantity(e.getQuantity())))
                .collect(Collectors.toList());
    }

}
