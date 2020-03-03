package kitchenpos.eatinorders.controller;

import kitchenpos.eatinorders.bo.OrderBo;
import kitchenpos.eatinorders.model.Order;
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

import static kitchenpos.eatinorders.Fixtures.ORDER_FOR_TABLE1_ID;
import static kitchenpos.eatinorders.Fixtures.orderForTable1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderRestController.class)
@Import(HttpEncodingAutoConfiguration.class)
class OrderRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderBo orderBo;

    @Test
    void create() throws Exception {
        // given
        given(orderBo.create(any(Order.class))).willReturn(orderForTable1());

        // when
        final ResultActions resultActions = mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderTableId\":1,\"orderLineItems\":[{\"menuId\":1,\"quantity\":2}]}")
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.orderTableId").isNumber())
                .andExpect(jsonPath("$.orderStatus").isString())
                .andExpect(jsonPath("$.orderedTime").isString())
                .andExpect(jsonPath("$.orderLineItems").isArray())
                .andExpect(jsonPath("$.orderLineItems[0].menuId").isNumber())
                .andExpect(jsonPath("$.orderLineItems[0].quantity").isNumber())
        ;
    }

    @Test
    void list() throws Exception {
        // given
        given(orderBo.list()).willReturn(Arrays.asList(orderForTable1()));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/api/orders"));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].orderTableId").isNumber())
                .andExpect(jsonPath("$[0].orderStatus").isString())
                .andExpect(jsonPath("$[0].orderedTime").isString())
                .andExpect(jsonPath("$[0].orderLineItems").isArray())
                .andExpect(jsonPath("$[0].orderLineItems[0].menuId").isNumber())
                .andExpect(jsonPath("$[0].orderLineItems[0].quantity").isNumber())
        ;
    }

    @Test
    void changeOrderStatus() throws Exception {
        // given
        given(orderBo.changeOrderStatus(eq(ORDER_FOR_TABLE1_ID), any(Order.class))).willReturn(orderForTable1());

        // when
        final ResultActions resultActions = mockMvc.perform(
                put("/api/orders/{orderId}/order-status", ORDER_FOR_TABLE1_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderStatus\":\"COOKING\"}")
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.orderTableId").isNumber())
                .andExpect(jsonPath("$.orderStatus").isString())
                .andExpect(jsonPath("$.orderedTime").isString())
                .andExpect(jsonPath("$.orderLineItems").isArray())
                .andExpect(jsonPath("$.orderLineItems[0].menuId").isNumber())
                .andExpect(jsonPath("$.orderLineItems[0].quantity").isNumber())
        ;
    }
}
