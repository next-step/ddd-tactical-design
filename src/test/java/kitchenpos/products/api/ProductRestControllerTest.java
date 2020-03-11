package kitchenpos.products.api;

import kitchenpos.products.application.ProductService;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.dto.ProductRequestDto;
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

import static kitchenpos.products.Fixtures.friedChicken;
import static kitchenpos.products.Fixtures.seasonedChicken;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductRestController.class)
@Import(HttpEncodingAutoConfiguration.class)
class ProductRestControllerTest {
    public static final Long FRIED_CHICKEN_ID = 1L;
    public static final Long SEASONED_CHICKEN_ID = 2L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void create() throws Exception {
        // given
        Product friedChickenProduct = friedChicken();

        given(productService.create(any(ProductRequestDto.class))).willReturn(friedChickenProduct);

        // when
        final ResultActions resultActions = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"후라이드\",\"price\":16000}")
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.price").isNumber())
        ;
    }

    @Test
    void list() throws Exception {
        // given
        Product friedChickenProduct = friedChicken();

        Product seasonedChickenProduct = seasonedChicken();

        given(productService.list()).willReturn(Arrays.asList(friedChickenProduct, seasonedChickenProduct));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/api/products"));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").isString())
                .andExpect(jsonPath("$[0].price").isNumber())
        ;
    }
}
