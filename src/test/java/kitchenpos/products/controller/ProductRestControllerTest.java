package kitchenpos.products.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.dto.ProductRequestDto;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductRestControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ProductRestController productRestController;

    @Mock
    private ProductService productService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public void setupMockMvc() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(productRestController)
                .alwaysDo(print())
                .build();
    }

    @Test
    void create() throws Exception {
        String name = "후라이드치킨";
        BigDecimal price = BigDecimal.valueOf(16000);
        Product saved = Product.registerProduct(name, price);
        saved.forceSetId(1L);
        given(productService.create(name, price)).willReturn(saved);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new ProductRequestDto(name, price))))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.price").isNumber())
        ;
    }

    @Test
    void list() throws Exception {
        given(productService.list()).willReturn(Collections.singletonList(
                Product.registerProduct("후라이드치킨", BigDecimal.valueOf(16000))
        ));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").isString())
                .andExpect(jsonPath("$[0].price").isNumber())
        ;
    }
}
