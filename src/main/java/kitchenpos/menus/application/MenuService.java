package kitchenpos.menus.application;

import java.util.*;
import kitchenpos.menus.application.dto.MenuChangePriceRequestDto;
import kitchenpos.menus.application.dto.MenuCreateRequestDto;
import kitchenpos.menus.application.dto.MenuCreateResponse;
import kitchenpos.menus.application.dto.MenuProductCreateRequestDto;
import kitchenpos.menus.application.dto.ProductConsumerDto;
import kitchenpos.menus.domain.menu.*;
import kitchenpos.menus.domain.menugroup.MenuGroup;
import kitchenpos.menus.domain.menugroup.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {
  private final MenuRepository menuRepository;
  private final MenuGroupRepository menuGroupRepository;
  private final MenuPurgomalumClient menuPurgomalumClient;
  private final ProductProviderInterface productProviderInterface;

  public MenuService(
      final MenuRepository menuRepository,
      final MenuGroupRepository menuGroupRepository,
      final MenuPurgomalumClient menuPurgomalumClient,
      final ProductProviderInterface productProviderInterface) {
    this.menuRepository = menuRepository;
    this.menuGroupRepository = menuGroupRepository;
    this.menuPurgomalumClient = menuPurgomalumClient;
    this.productProviderInterface = productProviderInterface;
  }

  @Transactional
  public MenuCreateResponse create(final MenuCreateRequestDto request) {
    final Menu menu =
        Menu.create(
            request.getName(),
            request.getPrice(),
            this.getMenuGroup(request),
            this.getMenuProducts(request),
            request.getDisplayed(),
            menuPurgomalumClient);

    menuRepository.save(menu);
    return MenuCreateResponse.of(menu);
  }

  @Transactional
  public MenuCreateResponse changePrice(final MenuChangePriceRequestDto menuChangePriceRequestDto) {
    final Menu menu =
        menuRepository
            .findById(menuChangePriceRequestDto.getMenuId())
            .orElseThrow(NoSuchElementException::new);

    menu.changePrice(menuChangePriceRequestDto.getMenuPrice());
    return MenuCreateResponse.of(menu);
  }

  @Transactional
  public MenuCreateResponse display(final UUID menuId) {
    final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
    menu.displayed();
    return MenuCreateResponse.of(menu);
  }

  @Transactional
  public MenuCreateResponse hide(final UUID menuId) {
    final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
    menu.hide();
    return MenuCreateResponse.of(menu);
  }

  @Transactional(readOnly = true)
  public List<MenuCreateResponse> findAll() {
    final List<Menu> menus = menuRepository.findAll();
    return menus.stream().map(MenuCreateResponse::of).toList();
  }

  private MenuGroup getMenuGroup(MenuCreateRequestDto request) {
    return menuGroupRepository
        .findById(request.getMenuGroupId())
        .orElseThrow(() -> new NoSuchElementException("메뉴 그룹이 존재하지 않습니다."));
  }

  private List<MenuProduct> getMenuProducts(MenuCreateRequestDto request) {
    if (Objects.isNull(request.getMenuProductCreateRequestDtos())) {
      throw new IllegalArgumentException();
    }

    final List<ProductConsumerDto> productConsumerDtos =
        request.getMenuProductCreateRequestDtos().stream()
            .map(MenuProductCreateRequestDto::to)
            .toList();

    return productProviderInterface.findByIds(productConsumerDtos);
  }
}
