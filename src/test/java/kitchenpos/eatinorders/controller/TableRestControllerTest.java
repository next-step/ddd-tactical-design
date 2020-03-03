package kitchenpos.eatinorders.controller;

import kitchenpos.eatinorders.bo.TableBo;
import kitchenpos.eatinorders.model.OrderTable;
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

import static kitchenpos.eatinorders.Fixtures.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TableRestController.class)
@Import(HttpEncodingAutoConfiguration.class)
class TableRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TableBo tableBo;

    @Test
    void create() throws Exception {
        // given
        given(tableBo.create(any(OrderTable.class))).willReturn(emptyTable1());

        // when
        final ResultActions resultActions = mockMvc.perform(post("/api/tables")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numberOfGuests\":0,\"empty\":true}")
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.tableGroupId").hasJsonPath())
                .andExpect(jsonPath("$.numberOfGuests").isNumber())
                .andExpect(jsonPath("$.empty").isBoolean())
        ;
    }

    @Test
    void list() throws Exception {
        // given
        given(tableBo.list()).willReturn(Arrays.asList(groupedTable1(), groupedTable2()));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/api/tables"));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].tableGroupId").isNumber())
                .andExpect(jsonPath("$[0].numberOfGuests").isNumber())
                .andExpect(jsonPath("$[0].empty").isBoolean())
        ;
    }

    @Test
    void changeEmpty() throws Exception {
        // given
        given(tableBo.changeEmpty(eq(TABLE1_ID), any(OrderTable.class))).willReturn(table1());

        // when
        final ResultActions resultActions = mockMvc.perform(
                put("/api/tables/{orderTableId}/empty", TABLE1_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"empty\":false}")
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.tableGroupId").hasJsonPath())
                .andExpect(jsonPath("$.numberOfGuests").isNumber())
                .andExpect(jsonPath("$.empty").isBoolean())
        ;
    }

    @Test
    void changeNumberOfGuests() throws Exception {
        // given
        given(tableBo.changeNumberOfGuests(eq(TABLE1_ID), any(OrderTable.class))).willReturn(table1());

        // when
        final ResultActions resultActions = mockMvc.perform(
                put("/api/tables/{orderTableId}/number-of-guests", TABLE1_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numberOfGuests\":0}")
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.tableGroupId").hasJsonPath())
                .andExpect(jsonPath("$.numberOfGuests").isNumber())
                .andExpect(jsonPath("$.empty").isBoolean())
        ;
    }
}
