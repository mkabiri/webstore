package com.mycompany.order.repository;

import com.mycompany.order.domain.SalesOrderItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SalesOrderItem entity.
 */
@Repository
public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, Long> {
    default Optional<SalesOrderItem> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SalesOrderItem> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SalesOrderItem> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct salesOrderItem from SalesOrderItem salesOrderItem left join fetch salesOrderItem.salesOrder",
        countQuery = "select count(distinct salesOrderItem) from SalesOrderItem salesOrderItem"
    )
    Page<SalesOrderItem> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct salesOrderItem from SalesOrderItem salesOrderItem left join fetch salesOrderItem.salesOrder")
    List<SalesOrderItem> findAllWithToOneRelationships();

    @Query(
        "select salesOrderItem from SalesOrderItem salesOrderItem left join fetch salesOrderItem.salesOrder where salesOrderItem.id =:id"
    )
    Optional<SalesOrderItem> findOneWithToOneRelationships(@Param("id") Long id);
}
