package com.mycompany.store.web.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class CustomerSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("name", table, columnPrefix + "_name"));
        columns.add(Column.aliased("phone_number", table, columnPrefix + "_phone_number"));
        columns.add(Column.aliased("gender", table, columnPrefix + "_gender"));
        columns.add(Column.aliased("address_line_1", table, columnPrefix + "_address_line_1"));
        columns.add(Column.aliased("address_line_2", table, columnPrefix + "_address_line_2"));
        columns.add(Column.aliased("address_line_3", table, columnPrefix + "_address_line_3"));
        columns.add(Column.aliased("address_line_4", table, columnPrefix + "_address_line_4"));
        columns.add(Column.aliased("town_city", table, columnPrefix + "_town_city"));
        columns.add(Column.aliased("county", table, columnPrefix + "_county"));
        columns.add(Column.aliased("zip", table, columnPrefix + "_zip"));

        columns.add(Column.aliased("user_id", table, columnPrefix + "_user_id"));
        return columns;
    }
}
