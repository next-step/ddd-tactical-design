package kitchenpos.menu.ui;

import static kitchenpos.TestFixtureFactory.createMenuGroup;
import static kitchenpos.TestFixtureFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.menu.domain.model.MenuProduct;
import kitchenpos.menu.domain.repository.MenuGroupRepository;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.menu.ui.dto.CreateMenuRq;
import kitchenpos.menu.ui.dto.CreateMenuRq.MenuProductRq;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MenuRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuGroupRepository menuGroupRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("메뉴를 생성한다")
    void create_menu_success() throws Exception {
        // given
        MenuGroup menuGroup = createAndSaveMenuGroup();
        Product product = createAndSaveProduct();
        CreateMenuRq request = createMenuRequest("김치찌개", 12000, menuGroup.getId(), product.getId());

        // when
        ResultActions result = mockMvc.perform(post("/api/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("김치찌개"))
                .andExpect(jsonPath("$.price").value(12000))
                .andExpect(jsonPath("$.menuGroupId").exists())
                .andExpect(jsonPath("$.menuProductRsList").isNotEmpty());
    }

    @Test
    @DisplayName("메뉴 상품 목록이 비어있으면 400 상태코드를 반환한다")
    void create_menuRequest_with_emptyProducts() throws Exception {
        // given
        MenuGroup menuGroup = createAndSaveMenuGroup();
        assertThatThrownBy(() -> {
            Menu request = new Menu("김치찌개", BigDecimal.valueOf(8000), true, null, menuGroup,
                    menuGroup.getId());

            // when
            ResultActions result = mockMvc.perform(post("/api/menus")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            // then
            result.andExpect(status().isBadRequest());
        }).isInstanceOf(NoSuchElementException.class)
                .hasMessage("메뉴 상품이 존재하지 않습니다!");
    }

    @Test
    @DisplayName("메뉴의 가격을 변경한다")
    void change_menuPrice() throws Exception {
        // given
        Menu menu = createAndSaveMenu(true);
        menu.changePrice(BigDecimal.valueOf(8001));

        // when
        ResultActions result = mockMvc.perform(put("/api/menus/{menuId}/price", menu.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menu)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(menu.getId().toString()))
                .andExpect(jsonPath("$.price").value(8001));
    }

    @Test
    @DisplayName("변경하려는 메뉴 가격이 음수이면 400 상태코드를 반환한다")
    void change_menuPrice_with_negativePrice() throws Exception {
        // given
        Menu savedMenu = createAndSaveMenu(true);
        assertThatThrownBy(() -> {
            Menu request = new Menu();
            request.changePrice(BigDecimal.valueOf(-1));

            // when
            ResultActions result = mockMvc.perform(put("/api/menus/{menuId}/price", savedMenu.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            // then
            result.andExpect(status().isBadRequest());
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 가격을 채워주세요!");
    }

    @Test
    @DisplayName("메뉴를 표시 상태로 변경한다")
    void display_menu() throws Exception {
        // given
        Menu savedMenu = createAndSaveMenu(false);

        // when
        ResultActions result = mockMvc.perform(put("/api/menus/{menuId}/display", savedMenu.getId()));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedMenu.getId().toString()))
                .andExpect(jsonPath("$.displayed").value(true));
    }

    @Test
    @DisplayName("메뉴를 숨김 상태로 변경한다")
    void hide_menu() throws Exception {
        // given
        Menu savedMenu = createAndSaveMenu(true);

        // when
        ResultActions result = mockMvc.perform(put("/api/menus/{menuId}/hide", savedMenu.getId()));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedMenu.getId().toString()))
                .andExpect(jsonPath("$.displayed").value(false));
    }

    @Test
    @DisplayName("모든 메뉴를 조회한다")
    void find_allMenus() throws Exception {
        // given
        createAndSaveMenu(true);
        createAndSaveMenu(true);

        // when
        ResultActions result = mockMvc.perform(get("/api/menus"));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    private MenuGroup createAndSaveMenuGroup() {
        return menuGroupRepository.save(createMenuGroup());
    }

    private Product createAndSaveProduct() {
        return productRepository.save(createProduct("김치", 5000));
    }

    private Menu createAndSaveMenu(boolean displayed) {
        MenuGroup menuGroup = createAndSaveMenuGroup();
        Product product = createAndSaveProduct();
        MenuProduct menuProduct = new MenuProduct(1, product, product.getId());
        Menu menu = new Menu("김치찌개", BigDecimal.valueOf(8000), displayed, List.of(menuProduct), menuGroup,
                menuGroup.getId());
        return menuRepository.save(menu);
    }

    private CreateMenuRq createMenuRequest(String name, int price, UUID menuGroupId, UUID productId) {
        return new CreateMenuRq(name, BigDecimal.valueOf(price), true, menuGroupId,
                List.of(new MenuProductRq(productId, 2)));
    }
}
