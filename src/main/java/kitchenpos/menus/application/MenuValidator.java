package kitchenpos.menus.application;

import kitchenpos.common.annotation.Validator;
import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.values.Price;
import kitchenpos.menus.dto.CreateMenuProductRequest;
import kitchenpos.menus.dto.CreateMenuRequest;
import kitchenpos.menus.tobe.domain.ToBeMenuProduct;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static kitchenpos.common.exception.KitchenPosExceptionType.BAD_REQUEST;

@Validator
public class MenuValidator {

    private final ProductRepository productRepository;

    public MenuValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void validatePrice(CreateMenuRequest request) {
        final List<CreateMenuProductRequest> menuProductRequests = request.getMenuProducts();
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new KitchenPosException("메뉴 구성 상품이 없으므로", BAD_REQUEST);
        }


    }

    public void validatePrice(Price price, List<ToBeMenuProduct> menuProducts) {
        List<Product> products = productRepository.findAllByIdIn(menuProducts.stream()
                .map(ToBeMenuProduct::getProductId)
                .collect(Collectors.toList()));
        Map<UUID, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
        final Price sum = menuProducts.stream()
                .map(e -> productMap.get(e.getProductId()).getPrice().multiply(e.getQuantity()))
                .reduce(Price.ZERO, Price::add);

        if (price.isGreaterThan(sum)) {
            throw new KitchenPosException("메뉴 가격이 구성 상품 가격보다 작으므로", BAD_REQUEST);
        }
    }
}
