package com.mycompany.order.service.mapper;

import com.mycompany.order.domain.SalesOrder;
import com.mycompany.order.service.dto.SalesOrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SalesOrder} and its DTO {@link SalesOrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface SalesOrderMapper extends EntityMapper<SalesOrderDTO, SalesOrder> {}
