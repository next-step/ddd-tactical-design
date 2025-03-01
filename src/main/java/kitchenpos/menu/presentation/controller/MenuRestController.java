package kitchenpos.menu.presentation.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.application.dto.MenuRequest;
import kitchenpos.menu.application.dto.MenuRequest.UpdatePrice;
import kitchenpos.menu.application.dto.MenuResponse;
import kitchenpos.menu.application.facade.MenuFacade;
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

    private final MenuFacade menuFacade;

    public MenuRestController(
        final MenuFacade menuFacade
    ) {
        this.menuFacade = menuFacade;
    }

    @PostMapping
    public ResponseEntity<MenuResponse.GetMenu> create(
        @RequestBody @Valid final MenuRequest.Create request
    ) {
        final MenuResponse.GetMenu response = menuFacade.create(request);
        return ResponseEntity.created(URI.create("/api/menus/" + response.id()))
            .body(response);
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<MenuResponse.GetMenu> changePrice(
        @PathVariable final UUID menuId,
        @RequestBody @Valid final MenuRequest.UpdatePrice request
    ) {
        return ResponseEntity.ok(menuFacade.changePrice(new UpdatePrice(menuId, request.price())));
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<MenuResponse.GetMenu> display(
        @PathVariable final UUID menuId
    ) {
        return ResponseEntity.ok(menuFacade.display(menuId));
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<MenuResponse.GetMenu> hide(
        @PathVariable final UUID menuId
    ) {
        return ResponseEntity.ok(menuFacade.hide(menuId));
    }

    @GetMapping
    public ResponseEntity<List<MenuResponse.GetMenu>> findAll() {
        return ResponseEntity.ok(menuFacade.findAll());
    }
}
