package com.mycompany.store.web.service.mapper;

import com.mycompany.store.web.domain.Customer;
import com.mycompany.store.web.domain.User;
import com.mycompany.store.web.service.dto.CustomerDTO;
import com.mycompany.store.web.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    CustomerDTO toDto(Customer s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
