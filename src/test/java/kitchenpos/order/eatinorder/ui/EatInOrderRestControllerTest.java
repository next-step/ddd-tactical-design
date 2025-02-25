package kitchenpos.order.eatinorder.ui;

import static kitchenpos.TestFixtureFactory.createEatInOrderRq;
import static kitchenpos.TestFixtureFactory.createMenu;
import static kitchenpos.TestFixtureFactory.createMenuGroup;
import static kitchenpos.TestFixtureFactory.createOrderLineItem;
import static kitchenpos.TestFixtureFactory.createProduct;
import static kitchenpos.TestFixtureFactory.createUsingOrderTable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.menu.domain.repository.MenuGroupRepository;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.order.common.model.OrderLineItem;
import kitchenpos.order.eatinorder.domain.model.EatInOrder;
import kitchenpos.order.eatinorder.domain.model.EatInOrderFlow;
import kitchenpos.order.eatinorder.domain.model.OrderTable;
import kitchenpos.order.eatinorder.domain.repository.EatInOrderRepository;
import kitchenpos.order.eatinorder.domain.repository.OrderTableRepository;
import kitchenpos.order.eatinorder.ui.dto.CreateEatInOrderRq;
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
class EatInOrderRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EatInOrderRepository eatInOrderRepository;
    @Autowired
    private OrderTableRepository orderTableRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuGroupRepository menuGroupRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("주문을 생성한다.")
    void create_success() throws Exception {
        // given
        Menu menu1 = createAndSaveMenu(createAndSaveMenuGroup(), createAndSaveProduct());
        Menu menu2 = createAndSaveMenu(createAndSaveMenuGroup(), createAndSaveProduct());
        OrderTable orderTable = createAndSaveUsingOrderTable();

        List<UUID> menuIds = List.of(menu1.getId(), menu2.getId());
        CreateEatInOrderRq request = createEatInOrderRq(menuIds, orderTable.getId());

        // when
        ResultActions result = mockMvc.perform(post("/api/eatInOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eatInOrderId").isNotEmpty())
                .andExpect(jsonPath("$.eatInOrderFlow").value(EatInOrderFlow.WAITING.toString()));
    }

    @Test
    @DisplayName("주문 상태가 주문 대기 중이라면 주문을 수락할 수 있다.")
    void accept_success() throws Exception {
        // given
        EatInOrder savedOrder = createAndSaveEatInOrder();

        // when
        ResultActions result = mockMvc.perform(put("/api/eatInOrder/{eatInOrderId}/accept", savedOrder.getId()));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.eatInOrderId").value(savedOrder.getId().toString()))
                .andExpect(jsonPath("$.eatInOrderFlow").value(EatInOrderFlow.ACCEPTED.toString()));
    }

    @Test
    @DisplayName("주문 상태가 접수 완료라면 서빙할 수 있다.")
    void serve_success() throws Exception {
        // given
        EatInOrder savedOrder = createAndSaveEatInOrder(EatInOrderFlow.ACCEPTED);

        // when
        ResultActions result = mockMvc.perform(put("/api/eatInOrder/{eatInOrderId}/serve", savedOrder.getId()));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.eatInOrderId").value(savedOrder.getId().toString()))
                .andExpect(jsonPath("$.eatInOrderFlow").value(EatInOrderFlow.SERVED.toString()));
    }

    @Test
    @DisplayName("주문 종류와 상태에 따라 주문을 완료할 수 있다.")
    void complete_success() throws Exception {
        // given
        EatInOrder savedOrder = createAndSaveEatInOrder(EatInOrderFlow.SERVED);

        // when
        ResultActions result = mockMvc.perform(put("/api/eatInOrder/{eatInOrderId}/complete", savedOrder.getId()));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.eatInOrderId").value(savedOrder.getId().toString()))
                .andExpect(jsonPath("$.eatInOrderFlow").value(EatInOrderFlow.COMPLETED.toString()));
    }

    @Test
    @DisplayName("전체 주문을 조회한다.")
    void findAll_success() throws Exception {
        // given
        createAndSaveEatInOrder();
        createAndSaveEatInOrder();

        // when
        ResultActions result = mockMvc.perform(get("/api/eatInOrder"));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    private EatInOrder createAndSaveEatInOrder() {
        MenuGroup menuGroup = createAndSaveMenuGroup();
        Product product = createAndSaveProduct();
        Menu menu = createAndSaveMenu(menuGroup, product);

        OrderLineItem orderLineItem = createOrderLineItem(menu);
        OrderTable orderTable = createAndSaveUsingOrderTable();

        EatInOrder eatInOrder = createEatInOrder(orderLineItem, orderTable, EatInOrderFlow.WAITING);
        return eatInOrderRepository.save(eatInOrder);
    }

    private EatInOrder createAndSaveEatInOrder(EatInOrderFlow flow) {
        MenuGroup menuGroup = createAndSaveMenuGroup();
        Product product = createAndSaveProduct();
        Menu menu = createAndSaveMenu(menuGroup, product);

        OrderLineItem orderLineItem = createOrderLineItem(menu);
        OrderTable orderTable = createAndSaveUsingOrderTable();

        EatInOrder eatInOrder = createEatInOrder(orderLineItem, orderTable, flow);
        return eatInOrderRepository.save(eatInOrder);
    }

    private EatInOrder createEatInOrder(OrderLineItem orderLineItem, OrderTable orderTable, EatInOrderFlow flow) {
        EatInOrder eatInOrder = new EatInOrder(LocalDateTime.now(), List.of(orderLineItem), flow);
        eatInOrder.occupyOrderTable(orderTable);
        return eatInOrder;
    }

    private OrderTable createAndSaveUsingOrderTable() {
        OrderTable orderTable = createUsingOrderTable();
        orderTableRepository.save(orderTable);
        return orderTable;
    }

    private Menu createAndSaveMenu(MenuGroup menuGroup, Product product) {
        Menu menu = createMenu(menuGroup, product);
        menuRepository.save(menu);
        return menu;
    }

    private Product createAndSaveProduct() {
        Product product = createProduct("김치", 4000);
        productRepository.save(product);
        return product;
    }

    private MenuGroup createAndSaveMenuGroup() {
        MenuGroup menuGroup = createMenuGroup();
        menuGroupRepository.save(menuGroup);
        return menuGroup;
    }
}
