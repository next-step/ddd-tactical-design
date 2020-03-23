package kitchenpos.menus.tobe.menu.application;

import kitchenpos.menus.tobe.menu.application.dto.MenuCreationRequestDto;
import kitchenpos.menus.tobe.menu.application.dto.MenuCreationResponseDto;
import kitchenpos.menus.tobe.menu.application.dto.ProductQuantityDto;
import kitchenpos.menus.tobe.menu.application.exception.MenuGroupNotExistsException;
import kitchenpos.menus.tobe.menu.domain.*;
import kitchenpos.menus.tobe.menuGroup.application.MenuGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final Products products;
    private final MenuGroupService menuGroupService;

    public MenuService(final MenuRepository menuRepository, final Products products, final MenuGroupService menuGroupService) {
        this.menuRepository = menuRepository;
        this.products = products;
        this.menuGroupService = menuGroupService;
    }

    public MenuCreationResponseDto create(final MenuCreationRequestDto menuCreationRequestDto) {

        try {
            menuGroupService.findById(menuCreationRequestDto.getMenuGroupId());
            System.out.println("findByIdfindByIdfindByIdfindByIdfindById");
        } catch (IllegalArgumentException e) {
            throw new MenuGroupNotExistsException();
        }

        // 제품 미지정 시 에러
        if (menuCreationRequestDto.getProductQuantityDtos() == null || menuCreationRequestDto.getProductQuantityDtos().isEmpty()) {
            throw new IllegalArgumentException("메뉴 내 제품을 1개 이상 지정해야합니다.");
        }

        // 제품 중복 시 에러
        if (menuCreationRequestDto.getProductQuantityDtos().size() != menuCreationRequestDto.getProductQuantityDtos()
                .stream()
                .map(ProductQuantityDto::getProductId)
                .distinct()
                .count()) {
            throw new IllegalArgumentException("메뉴 내 제품은 중복될 수 없습니다.");
        }

        // 제품 Id 리스트 생성
        final List<Long> productIds = menuCreationRequestDto.getProductQuantityDtos()
                .stream()
                .map(p -> p.getProductId())
                .collect(Collectors.toList());

        // 제품의 가격 정보 가져오기 (Anti-Corruption Layer)
        final List<ProductPriceDto> productPriceDtos = products.getProductPricesByProductIds(productIds);

        final List<MenuProduct> menuProducts = menuCreationRequestDto.getProductQuantityDtos()
                .stream()
                .map(productQuantityDto -> {
                    final BigDecimal price = productPriceDtos.stream()
                            .filter(productPrice -> productPrice.getProductId() == productQuantityDto.getProductId())
                            .findFirst()
                            .get()
                            .getPrice();
                    return new MenuProduct(productQuantityDto.getProductId(), price, productQuantityDto.getQuantity());
                }).collect(Collectors.toList());

        final Menu newMenu = new Menu(menuCreationRequestDto.getName(), menuCreationRequestDto.getPrice(), menuCreationRequestDto.getMenuGroupId(), menuProducts);

        return new MenuCreationResponseDto(menuRepository.save(newMenu).getId());
    }

    public List<Menu> list() {
        return menuRepository.findAll();
    }
}
