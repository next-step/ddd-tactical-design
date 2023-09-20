package kitchenpos.products.tobe.domain;

import kitchenpos.menus.shared.dto.MenuProductDto;
import kitchenpos.menus.tobe.domain.menu.ProductPriceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductPriceServiceImpl implements ProductPriceService {
    private final ProductRepository productRepository;

    public ProductPriceServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void validateMenuProductDtoRequest(List<MenuProductDto> menuProductRequests) {
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Product> products = productRepository.findAllByIdIn(
                menuProductRequests.stream()
                        .map(MenuProductDto::getProductId)
                        .collect(Collectors.toList())
        );
        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }
    }
}
