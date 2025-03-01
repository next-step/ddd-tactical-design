package kitchenpos.menu.presentation.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import kitchenpos.menu.application.dto.MenuGroupRequest;
import kitchenpos.menu.application.dto.MenuGroupResponse;
import kitchenpos.menu.application.facade.MenuGroupFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/menu-groups")
@RestController
public class MenuGroupRestController {

    private final MenuGroupFacade menuGroupFacade;

    public MenuGroupRestController(
        final MenuGroupFacade menuGroupFacade
    ) {
        this.menuGroupFacade = menuGroupFacade;
    }

    @PostMapping
    public ResponseEntity<MenuGroupResponse.GetGroup> create(
        @RequestBody @Valid final MenuGroupRequest.Create request
    ) {
        final MenuGroupResponse.GetGroup response = menuGroupFacade.create(request);
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.id()))
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroupResponse.GetGroup>> findAll() {
        return ResponseEntity.ok(menuGroupFacade.findAll());
    }
}
