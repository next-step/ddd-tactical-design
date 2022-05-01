package kitchenpos.menus.ui;

import kitchenpos.menus.application.TobeMenuGroupService;
import kitchenpos.menus.dto.MenuGroupDto;
import kitchenpos.menus.dto.MenuGroupRegisterRequest;
import kitchenpos.menus.dto.MenuGroupRegisterResponse;
import kitchenpos.support.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/menu-groups/tobe")
@RestController
public class TobeMenuGroupRestController {
    private final TobeMenuGroupService menuGroupService;

    public TobeMenuGroupRestController(final TobeMenuGroupService menuGroupService) {
        this.menuGroupService = menuGroupService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody final MenuGroupRegisterRequest request,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponse("400", "올바르지 않은 메뉴그룹 생성 요청입니다", errors));
        }
        final MenuGroupRegisterResponse response = menuGroupService.create(request);
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroupDto>> findAll() {
        return ResponseEntity.ok(
                menuGroupService.findAll()
        );
    }
}
