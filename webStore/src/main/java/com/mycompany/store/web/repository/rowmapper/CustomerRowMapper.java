package com.mycompany.store.web.repository.rowmapper;

import com.mycompany.store.web.domain.Customer;
import com.mycompany.store.web.domain.enumeration.Gender;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Customer}, with proper type conversions.
 */
@Service
public class CustomerRowMapper implements BiFunction<Row, String, Customer> {

    private final ColumnConverter converter;

    public CustomerRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Customer} stored in the database.
     */
    @Override
    public Customer apply(Row row, String prefix) {
        Customer entity = new Customer();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setPhoneNumber(converter.fromRow(row, prefix + "_phone_number", String.class));
        entity.setGender(converter.fromRow(row, prefix + "_gender", Gender.class));
        entity.setAddressLine1(converter.fromRow(row, prefix + "_address_line_1", String.class));
        entity.setAddressLine2(converter.fromRow(row, prefix + "_address_line_2", String.class));
        entity.setAddressLine3(converter.fromRow(row, prefix + "_address_line_3", String.class));
        entity.setAddressLine4(converter.fromRow(row, prefix + "_address_line_4", String.class));
        entity.setTownCity(converter.fromRow(row, prefix + "_town_city", String.class));
        entity.setCounty(converter.fromRow(row, prefix + "_county", String.class));
        entity.setZip(converter.fromRow(row, prefix + "_zip", String.class));
        entity.setUserId(converter.fromRow(row, prefix + "_user_id", String.class));
        return entity;
    }
}
