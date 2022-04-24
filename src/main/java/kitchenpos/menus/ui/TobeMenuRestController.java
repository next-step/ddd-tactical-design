package kitchenpos.menus.ui;

import kitchenpos.menus.application.TobeMenuService;
import kitchenpos.menus.domain.tobe.domain.vo.MenuId;
import kitchenpos.menus.dto.*;
import kitchenpos.support.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/menus")
@RestController
public class TobeMenuRestController {
    private final TobeMenuService menuService;

    public TobeMenuRestController(final TobeMenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody final MenuRegisterRequest request,
                                    final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return badRequestOf(bindingResult);
        }
        final MenuRegisterResponse response = menuService.create(request);
        return ResponseEntity.created(URI.create("/api/menus/" + response.getMenuId()))
                .body(response);
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<?> changePrice(@PathVariable final MenuId menuId, @Valid @RequestBody final MenuPriceChangeRequest request,
                                         final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return badRequestOf(bindingResult);
        }
        request.setMenuId(menuId);
        final MenuPriceChangeResponse response = menuService.changePrice(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<?> display(@Valid @PathVariable final MenuDisplayRequest request,
                                     final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return badRequestOf(bindingResult);
        }
        final MenuDisplayResponse response = menuService.display(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<?> hide(@Valid @PathVariable final MenuHideRequest request,
                                  final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return badRequestOf(bindingResult);
        }
        final MenuHideResponse response = menuService.hide(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuDto>> findAll() {
        return ResponseEntity.ok(menuService.findAll());
    }

    private ResponseEntity badRequestOf(final BindingResult bindingResult) {
        List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new ErrorResponse("400", "올바르지 않은 메뉴 생성 요청입니다", errors));
    }
}
