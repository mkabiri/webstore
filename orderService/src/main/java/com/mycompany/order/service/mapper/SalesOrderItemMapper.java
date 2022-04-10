package com.mycompany.order.service.mapper;

import com.mycompany.order.domain.SalesOrder;
import com.mycompany.order.domain.SalesOrderItem;
import com.mycompany.order.service.dto.SalesOrderDTO;
import com.mycompany.order.service.dto.SalesOrderItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SalesOrderItem} and its DTO {@link SalesOrderItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface SalesOrderItemMapper extends EntityMapper<SalesOrderItemDTO, SalesOrderItem> {
    @Mapping(target = "salesOrder", source = "salesOrder", qualifiedByName = "salesOrderSalesOrderNumber")
    SalesOrderItemDTO toDto(SalesOrderItem s);

    @Named("salesOrderSalesOrderNumber")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "salesOrderNumber", source = "salesOrderNumber")
    SalesOrderDTO toDtoSalesOrderSalesOrderNumber(SalesOrder salesOrder);
}
