package kitchenpos.products.controller;

import kitchenpos.products.bo.ProductBo;
import kitchenpos.products.model.ProductData;
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

import static kitchenpos.products.Fixtures.friedChickenData;
import static kitchenpos.products.Fixtures.seasonedChickenData;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductRestController.class)
@Import(HttpEncodingAutoConfiguration.class)
class ProductDataRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductBo productBo;

    @Test
    void create() throws Exception {
        // given
        given(productBo.create(any(ProductData.class))).willReturn(friedChickenData());

        // when
        final ResultActions resultActions = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"후라이드\",\"price\":16000}")
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.price").isNumber())
        ;
    }

    @Test
    void list() throws Exception {
        // given
        given(productBo.list()).willReturn(Arrays.asList(friedChickenData(), seasonedChickenData()));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/api/products"));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").isString())
                .andExpect(jsonPath("$[0].price").isNumber())
        ;
    }
}
