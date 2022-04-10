package com.mycompany.order.service;

import com.mycompany.order.domain.SalesOrder;
import com.mycompany.order.repository.SalesOrderRepository;
import com.mycompany.order.service.dto.SalesOrderDTO;
import com.mycompany.order.service.mapper.SalesOrderMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SalesOrder}.
 */
@Service
@Transactional
public class SalesOrderService {

    private final Logger log = LoggerFactory.getLogger(SalesOrderService.class);

    private final SalesOrderRepository salesOrderRepository;

    private final SalesOrderMapper salesOrderMapper;

    public SalesOrderService(SalesOrderRepository salesOrderRepository, SalesOrderMapper salesOrderMapper) {
        this.salesOrderRepository = salesOrderRepository;
        this.salesOrderMapper = salesOrderMapper;
    }

    /**
     * Save a salesOrder.
     *
     * @param salesOrderDTO the entity to save.
     * @return the persisted entity.
     */
    public SalesOrderDTO save(SalesOrderDTO salesOrderDTO) {
        log.debug("Request to save SalesOrder : {}", salesOrderDTO);
        SalesOrder salesOrder = salesOrderMapper.toEntity(salesOrderDTO);
        salesOrder = salesOrderRepository.save(salesOrder);
        return salesOrderMapper.toDto(salesOrder);
    }

    /**
     * Update a salesOrder.
     *
     * @param salesOrderDTO the entity to save.
     * @return the persisted entity.
     */
    public SalesOrderDTO update(SalesOrderDTO salesOrderDTO) {
        log.debug("Request to save SalesOrder : {}", salesOrderDTO);
        SalesOrder salesOrder = salesOrderMapper.toEntity(salesOrderDTO);
        salesOrder = salesOrderRepository.save(salesOrder);
        return salesOrderMapper.toDto(salesOrder);
    }

    /**
     * Partially update a salesOrder.
     *
     * @param salesOrderDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SalesOrderDTO> partialUpdate(SalesOrderDTO salesOrderDTO) {
        log.debug("Request to partially update SalesOrder : {}", salesOrderDTO);

        return salesOrderRepository
            .findById(salesOrderDTO.getId())
            .map(existingSalesOrder -> {
                salesOrderMapper.partialUpdate(existingSalesOrder, salesOrderDTO);

                return existingSalesOrder;
            })
            .map(salesOrderRepository::save)
            .map(salesOrderMapper::toDto);
    }

    /**
     * Get all the salesOrders.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SalesOrderDTO> findAll() {
        log.debug("Request to get all SalesOrders");
        return salesOrderRepository.findAll().stream().map(salesOrderMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one salesOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalesOrderDTO> findOne(Long id) {
        log.debug("Request to get SalesOrder : {}", id);
        return salesOrderRepository.findById(id).map(salesOrderMapper::toDto);
    }

    /**
     * Delete the salesOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SalesOrder : {}", id);
        salesOrderRepository.deleteById(id);
    }
}
