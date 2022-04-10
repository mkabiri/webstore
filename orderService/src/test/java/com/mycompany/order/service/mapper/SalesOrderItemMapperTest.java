package com.mycompany.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SalesOrderItemMapperTest {

    private SalesOrderItemMapper salesOrderItemMapper;

    @BeforeEach
    public void setUp() {
        salesOrderItemMapper = new SalesOrderItemMapperImpl();
    }
}
