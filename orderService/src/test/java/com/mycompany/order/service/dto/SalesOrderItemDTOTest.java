package com.mycompany.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalesOrderItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesOrderItemDTO.class);
        SalesOrderItemDTO salesOrderItemDTO1 = new SalesOrderItemDTO();
        salesOrderItemDTO1.setId(1L);
        SalesOrderItemDTO salesOrderItemDTO2 = new SalesOrderItemDTO();
        assertThat(salesOrderItemDTO1).isNotEqualTo(salesOrderItemDTO2);
        salesOrderItemDTO2.setId(salesOrderItemDTO1.getId());
        assertThat(salesOrderItemDTO1).isEqualTo(salesOrderItemDTO2);
        salesOrderItemDTO2.setId(2L);
        assertThat(salesOrderItemDTO1).isNotEqualTo(salesOrderItemDTO2);
        salesOrderItemDTO1.setId(null);
        assertThat(salesOrderItemDTO1).isNotEqualTo(salesOrderItemDTO2);
    }
}
