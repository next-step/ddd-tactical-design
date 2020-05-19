package kitchenpos.menus.controller;

import kitchenpos.menus.bo.MenuGroupBo;
import kitchenpos.menus.model.MenuGroup;
import kitchenpos.menus.model.MenuGroupCreateRequest;
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

import static kitchenpos.menus.Fixtures.twoChickens;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MenuGroupRestController.class)
@Import(HttpEncodingAutoConfiguration.class)
class MenuGroupRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuGroupBo menuGroupBo;

    @Test
    void create() throws Exception {
        // given
        given(menuGroupBo.create(any(MenuGroupCreateRequest.class))).willReturn(twoChickens());

        // when
        final ResultActions resultActions = mockMvc.perform(post("/api/menu-groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"두마리메뉴\"}")
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isString())
        ;
    }

    @Test
    void list() throws Exception {
        // given
        given(menuGroupBo.list()).willReturn(Arrays.asList(twoChickens()));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/api/menu-groups"));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").isString())
        ;
    }
}
