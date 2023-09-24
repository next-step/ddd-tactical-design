package kitchenpos.products.acceptance;

import static kitchenpos.products.acceptance.ProductApi.*;
import static kitchenpos.products.acceptance.ProductData.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductAcceptanceTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    void 상품_생성__성공() throws Exception {
        Map<String, Object> request = 강정치킨.getValue();

        MockHttpServletResponse response = 상품_생성_요청(mockMvc, request);

        상품_생성_성공함(response);
    }

    @Test
    void 상품_가격_변경__성공() throws Exception {
        String productId = 상품_생성(mockMvc, 강정치킨.getValue());
        Map<String, Object> request = Map.of("price", 17000);

        MockHttpServletResponse response = 상품_가격_변경_요청(mockMvc, productId, request);

        상품_가격_변경_성공함(response);
    }

    @Test
    void 상품_전체조회() throws Exception {
        Map<String, Object> request = 강정치킨.getValue();
        상품_생성_요청(mockMvc, request);

        Map<String, Object> request2 = 양념치킨.getValue();
        상품_생성_요청(mockMvc, request2);

        MockHttpServletResponse response = 상품_전체조회_요청(mockMvc);

        상품_전체조회_성공함(response, 2);
    }
}