package kitchenpos.eatinorders.controller;

import kitchenpos.eatinorders.bo.TableGroupBo;
import kitchenpos.eatinorders.model.TableGroup;
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

import static kitchenpos.eatinorders.Fixtures.TABLE1_AND_TABLE2_ID;
import static kitchenpos.eatinorders.Fixtures.table1AndTable2;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TableGroupRestController.class)
@Import(HttpEncodingAutoConfiguration.class)
class TableGroupRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TableGroupBo tableGroupBo;

    @Test
    void create() throws Exception {
        // given
        given(tableGroupBo.create(any(TableGroup.class))).willReturn(table1AndTable2());

        // when
        final ResultActions resultActions = mockMvc.perform(post("/api/table-groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderTables\":[{\"id\":1},{\"id\":2}]}")
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.createdDate").isString())
                .andExpect(jsonPath("$.orderTables").isArray())
                .andExpect(jsonPath("$.orderTables[0].id").isNumber())
        ;
    }

    @Test
    void ungroup() throws Exception {
        // given
        // when
        final ResultActions resultActions = mockMvc.perform(
                delete("/api/table-groups/{tableGroupId}", TABLE1_AND_TABLE2_ID)
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isNoContent())
        ;
    }
}
