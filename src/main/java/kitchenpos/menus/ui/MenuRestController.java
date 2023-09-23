package kitchenpos.menus.ui;

import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.application.dto.MenuResponse;
import kitchenpos.menus.ui.dto.MenuRestMapper;
import kitchenpos.menus.ui.dto.request.MenuChangePriceRestRequest;
import kitchenpos.menus.ui.dto.request.MenuCreateRestRequest;
import kitchenpos.menus.ui.dto.response.MenuRestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/menus")
@RestController
public class MenuRestController {
    private final MenuService menuService;

    public MenuRestController(final MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<MenuRestResponse> create(@RequestBody final MenuCreateRestRequest request) {
        final MenuResponse response = menuService.create(MenuRestMapper.toDto(request));
        return ResponseEntity.created(URI.create("/api/menus/" + response.getId()))
                .body(MenuRestMapper.toRestDto(response));
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<MenuRestResponse> changePrice(@PathVariable final UUID menuId, @RequestBody final MenuChangePriceRestRequest request) {
        MenuResponse response = menuService.changePrice(menuId, MenuRestMapper.toDto(request));
        return ResponseEntity.ok(MenuRestMapper.toRestDto(response));
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<MenuRestResponse> display(@PathVariable final UUID menuId) {
        MenuResponse response = menuService.display(menuId);
        return ResponseEntity.ok(MenuRestMapper.toRestDto(response));
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<MenuRestResponse> hide(@PathVariable final UUID menuId) {
        MenuResponse response = menuService.hide(menuId);
        return ResponseEntity.ok(MenuRestMapper.toRestDto(response));
    }

    @GetMapping
    public ResponseEntity<List<MenuRestResponse>> findAll() {
        List<MenuResponse> responses = menuService.findAll();
        return ResponseEntity.ok(MenuRestMapper.toRestDtos(responses));
    }
}
