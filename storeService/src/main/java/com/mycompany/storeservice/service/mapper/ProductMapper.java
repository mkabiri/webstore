package com.mycompany.storeservice.service.mapper;

import com.mycompany.storeservice.domain.Product;
import com.mycompany.storeservice.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {}
