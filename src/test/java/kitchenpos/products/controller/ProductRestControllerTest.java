package kitchenpos.products.controller;

import kitchenpos.products.Fixtures;
import kitchenpos.products.bo.ProductBo;
import kitchenpos.products.tobe.domain.model.Product;
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

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductRestController.class)
@Import(HttpEncodingAutoConfiguration.class)
class ProductRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductBo productBo;

    @Test
    void create() throws Exception {
        // given
        final Product expected = new Product(Fixtures.FRIED_CHICKEN_ID, "후라이드", BigDecimal.valueOf(16_000L));
        given(productBo.create(any(Product.class))).willReturn(expected);

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
        final Product friedChicken = new Product(Fixtures.FRIED_CHICKEN_ID, "후라이드", BigDecimal.valueOf(16_000L));
        final Product seasonedChicken = new Product(Fixtures.SEASONED_CHICKEN_ID, "양념치킨", BigDecimal.valueOf(16_000L));
        given(productBo.list()).willReturn(Arrays.asList(friedChicken, seasonedChicken));

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
