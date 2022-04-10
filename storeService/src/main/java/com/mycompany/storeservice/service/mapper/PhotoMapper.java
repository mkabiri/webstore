package com.mycompany.storeservice.service.mapper;

import com.mycompany.storeservice.domain.Photo;
import com.mycompany.storeservice.domain.Product;
import com.mycompany.storeservice.service.dto.PhotoDTO;
import com.mycompany.storeservice.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Photo} and its DTO {@link PhotoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PhotoMapper extends EntityMapper<PhotoDTO, Photo> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productName")
    PhotoDTO toDto(Photo s);

    @Named("productName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProductDTO toDtoProductName(Product product);
}
