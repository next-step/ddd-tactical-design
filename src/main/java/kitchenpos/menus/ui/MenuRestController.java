package kitchenpos.menus.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.application.dto.MenuChangePriceRequestDto;
import kitchenpos.menus.application.dto.MenuCreateRequestDto;
import kitchenpos.menus.application.dto.MenuCreateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/menus")
@RestController
public class MenuRestController {
  private final MenuService menuService;

  public MenuRestController(final MenuService menuService) {
    this.menuService = menuService;
  }

  @PostMapping
  public ResponseEntity<MenuCreateResponse> create(
      @RequestBody final MenuCreateRequestDto request) {
    final MenuCreateResponse response = menuService.create(request);
    return ResponseEntity.created(URI.create("/api/menus/" + response.getMenuId())).body(response);
  }

  @PutMapping("/{menuId}/price")
  public ResponseEntity<MenuCreateResponse> changePrice(
      @PathVariable final UUID menuId, @RequestBody final MenuChangePriceRequestDto request) {
    final MenuChangePriceRequestDto menuChangePriceRequestDto =
        new MenuChangePriceRequestDto(menuId, request.getMenuPrice());
    return ResponseEntity.ok(menuService.changePrice(menuChangePriceRequestDto));
  }

  @PutMapping("/{menuId}/display")
  public ResponseEntity<MenuCreateResponse> display(@PathVariable final UUID menuId) {
    return ResponseEntity.ok(menuService.display(menuId));
  }

  @PutMapping("/{menuId}/hide")
  public ResponseEntity<MenuCreateResponse> hide(@PathVariable final UUID menuId) {
    return ResponseEntity.ok(menuService.hide(menuId));
  }

  @GetMapping
  public ResponseEntity<List<MenuCreateResponse>> findAll() {
    return ResponseEntity.ok(menuService.findAll());
  }
}
