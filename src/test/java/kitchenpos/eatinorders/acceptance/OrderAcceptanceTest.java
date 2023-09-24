package kitchenpos.eatinorders.acceptance;

import static kitchenpos.eatinorders.acceptance.OrderApi.*;
import static kitchenpos.eatinorders.acceptance.OrderTableApi.*;
import static kitchenpos.menus.acceptance.MenuApi.메뉴_생성;
import static kitchenpos.menus.acceptance.MenuData.강정치킨_2마리;
import static kitchenpos.menus.acceptance.MenuGroupApi.메뉴그룹_생성;
import static kitchenpos.menus.acceptance.MenuGroupData.추천_메뉴;
import static kitchenpos.products.acceptance.ProductApi.상품_생성;
import static kitchenpos.products.acceptance.ProductData.강정치킨;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
public class OrderAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    private String menuGroupId;
    private String productId;
    private String menuId;
    private String orderTableId;

    @BeforeEach
    void setUp() throws Exception {
        menuGroupId = 메뉴그룹_생성(mockMvc, 추천_메뉴.getValue());
        productId = 상품_생성(mockMvc, 강정치킨.getValue());
        menuId = 메뉴_생성(mockMvc, 강정치킨_2마리(menuGroupId, productId));
        orderTableId = 주문테이블_생성(mockMvc, Map.of("name", "1번 테이블"));
    }

    @Test
    void 먹고가기_주문_통합테스트__성공() throws Exception {
        // 먹고가기 주문 생성 요청 테스트
        주문테이블_착석_요청(mockMvc, orderTableId);
        Map<String, Object> request = Map.of(
                "type", "EAT_IN",
                "orderTableId", orderTableId,
                "orderLineItems", List.of(
                        Map.of("menuId", menuId,
                                "price", 19000,
                                "quantity", 1
                        )
                ));

        MockHttpServletResponse orderCreatedResponse = 주문_생성_요청(mockMvc, request);
        주문_생성_성공함(orderCreatedResponse);

        // 주문 수락 요청 테스트
        String orderId = extractOrderId(orderCreatedResponse);

        MockHttpServletResponse orderAcceptedResponse = 주문_수락_요청(mockMvc, orderId);
        주문_수락됨(orderAcceptedResponse);

        // 주문 서빙 요청 테스트
        MockHttpServletResponse orderServedResponse = 주문_서빙_요청(mockMvc, orderId);
        주문_서빙됨(orderServedResponse);


        // 주문 완료 요청 테스트
        MockHttpServletResponse orderCompletedResponse = 주문_완료_요청(mockMvc, orderId);
        주문_완료됨(orderCompletedResponse);
    }

    @Disabled
    @Test
    void 배달_주문_통합테스트__성공() throws Exception {
        // 배달 주문 생성 요청 테스트
        Map<String, Object> request = Map.of(
                "type", "DELIVERY",
                "deliveryAddress", "서울시 영등포구",
                "orderLineItems", List.of(
                        Map.of("menuId", menuId,
                                "price", 19000,
                                "quantity", 1
                        )
                ));

        MockHttpServletResponse orderCreatedResponse = 주문_생성_요청(mockMvc, request);
        주문_생성_성공함(orderCreatedResponse);

        // 주문 수락 요청 테스트
        String orderId = extractOrderId(orderCreatedResponse);

        MockHttpServletResponse orderAcceptedResponse = 주문_수락_요청(mockMvc, orderId);
        주문_수락됨(orderAcceptedResponse);


        // 주문 서빙 요청 테스트
        MockHttpServletResponse orderServedResponse = 주문_서빙_요청(mockMvc, orderId);
        주문_서빙됨(orderServedResponse);


        // 주문 배달 시작 요청 테스트
        MockHttpServletResponse orderDeliveryStartedResponse = 주문_배달_시작_요청(mockMvc, orderId);
        주문_배달_시작됨(orderDeliveryStartedResponse);


        // 주문 배달 완료 요청 테스트
        MockHttpServletResponse orderDeliveryCompletedResponse = 주문_배달_완료_요청(mockMvc, orderId);
        주문_배달_완료됨(orderDeliveryCompletedResponse);


        // 주문 완료 요청 테스트
        MockHttpServletResponse orderCompletedResponse = 주문_완료_요청(mockMvc, orderId);
        주문_완료됨(orderCompletedResponse);
    }

    @Disabled
    @Test
    void 포장_주문_통합테스트__성공() throws Exception {
        // 포장 주문 생성 요청 테스트
        Map<String, Object> request = Map.of(
                "type", "TAKEOUT",
                "orderLineItems", List.of(
                        Map.of("menuId", menuId,
                                "price", 19000,
                                "quantity", 1
                        )
                ));

        MockHttpServletResponse orderCreatedResponse = 주문_생성_요청(mockMvc, request);
        주문_생성_성공함(orderCreatedResponse);

        // 주문 수락 요청 테스트
        String orderId = extractOrderId(orderCreatedResponse);

        MockHttpServletResponse orderAcceptedResponse = 주문_수락_요청(mockMvc, orderId);
        주문_수락됨(orderAcceptedResponse);

        // 주문 서빙 요청 테스트
        MockHttpServletResponse orderServedResponse = 주문_서빙_요청(mockMvc, orderId);
        주문_서빙됨(orderServedResponse);


        // 주문 완료 요청 테스트
        MockHttpServletResponse orderCompletedResponse = 주문_완료_요청(mockMvc, orderId);
        주문_완료됨(orderCompletedResponse);
    }

    @Test
    void 주문_전체조회() throws Exception {
        Map<String, Object> request = Map.of(
                "type", "EAT_IN",
                "orderTableId", orderTableId,
                "orderLineItems", List.of(
                        Map.of("menuId", menuId,
                                "price", 19000,
                                "quantity", 1
                        )
                ));

        주문테이블_착석_요청(mockMvc, orderTableId);
        주문_생성_요청(mockMvc, request);
        주문_생성_요청(mockMvc, request);

        MockHttpServletResponse response = 주문_전체조회_요청(mockMvc);
        주문_전체조회_성공함(response, 2);
    }
}