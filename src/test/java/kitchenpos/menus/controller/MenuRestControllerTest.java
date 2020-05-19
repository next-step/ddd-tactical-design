package kitchenpos.menus.controller;

import kitchenpos.menus.bo.MenuBo;
import kitchenpos.menus.model.MenuCreateRequest;
import kitchenpos.menus.tobe.domain.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static kitchenpos.menus.Fixtures.twoFriedChickens;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MenuRestController.class)
@Import(HttpEncodingAutoConfiguration.class)
class MenuRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuBo menuBo;

    @Test
    void create() throws Exception {
        // given
        given(menuBo.create(any(MenuCreateRequest.class))).willReturn(twoFriedChickens());

        // when
        final ResultActions resultActions = mockMvc.perform(post("/api/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"후라이드+후라이드\",\"price\":19000,\"menuGroupId\":1,"
                        + "\"menuProducts\":[{\"productId\":1,\"quantity\":2}]}")
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.price").isNumber())
                .andExpect(jsonPath("$.menuGroupId").isNumber())
                .andExpect(jsonPath("$.menuProducts").isArray())
                .andExpect(jsonPath("$.menuProducts[0].productId").isNumber())
                .andExpect(jsonPath("$.menuProducts[0].quantity").isNumber())
        ;
    }

    @Test
    void list() throws Exception {
        // given
        given(menuBo.list()).willReturn(Arrays.asList(twoFriedChickens()));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/api/menus"));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").isString())
                .andExpect(jsonPath("$[0].price").isNumber())
                .andExpect(jsonPath("$[0].menuGroupId").isNumber())
                .andExpect(jsonPath("$[0].menuProducts").isArray())
                .andExpect(jsonPath("$[0].menuProducts[0].productId").isNumber())
                .andExpect(jsonPath("$[0].menuProducts[0].quantity").isNumber())
        ;
    }
}
