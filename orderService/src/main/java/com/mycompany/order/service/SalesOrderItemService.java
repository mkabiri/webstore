package com.mycompany.order.service;

import com.mycompany.order.domain.SalesOrderItem;
import com.mycompany.order.repository.SalesOrderItemRepository;
import com.mycompany.order.service.dto.SalesOrderItemDTO;
import com.mycompany.order.service.mapper.SalesOrderItemMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SalesOrderItem}.
 */
@Service
@Transactional
public class SalesOrderItemService {

    private final Logger log = LoggerFactory.getLogger(SalesOrderItemService.class);

    private final SalesOrderItemRepository salesOrderItemRepository;

    private final SalesOrderItemMapper salesOrderItemMapper;

    public SalesOrderItemService(SalesOrderItemRepository salesOrderItemRepository, SalesOrderItemMapper salesOrderItemMapper) {
        this.salesOrderItemRepository = salesOrderItemRepository;
        this.salesOrderItemMapper = salesOrderItemMapper;
    }

    /**
     * Save a salesOrderItem.
     *
     * @param salesOrderItemDTO the entity to save.
     * @return the persisted entity.
     */
    public SalesOrderItemDTO save(SalesOrderItemDTO salesOrderItemDTO) {
        log.debug("Request to save SalesOrderItem : {}", salesOrderItemDTO);
        SalesOrderItem salesOrderItem = salesOrderItemMapper.toEntity(salesOrderItemDTO);
        salesOrderItem = salesOrderItemRepository.save(salesOrderItem);
        return salesOrderItemMapper.toDto(salesOrderItem);
    }

    /**
     * Update a salesOrderItem.
     *
     * @param salesOrderItemDTO the entity to save.
     * @return the persisted entity.
     */
    public SalesOrderItemDTO update(SalesOrderItemDTO salesOrderItemDTO) {
        log.debug("Request to save SalesOrderItem : {}", salesOrderItemDTO);
        SalesOrderItem salesOrderItem = salesOrderItemMapper.toEntity(salesOrderItemDTO);
        salesOrderItem = salesOrderItemRepository.save(salesOrderItem);
        return salesOrderItemMapper.toDto(salesOrderItem);
    }

    /**
     * Partially update a salesOrderItem.
     *
     * @param salesOrderItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SalesOrderItemDTO> partialUpdate(SalesOrderItemDTO salesOrderItemDTO) {
        log.debug("Request to partially update SalesOrderItem : {}", salesOrderItemDTO);

        return salesOrderItemRepository
            .findById(salesOrderItemDTO.getId())
            .map(existingSalesOrderItem -> {
                salesOrderItemMapper.partialUpdate(existingSalesOrderItem, salesOrderItemDTO);

                return existingSalesOrderItem;
            })
            .map(salesOrderItemRepository::save)
            .map(salesOrderItemMapper::toDto);
    }

    /**
     * Get all the salesOrderItems.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SalesOrderItemDTO> findAll() {
        log.debug("Request to get all SalesOrderItems");
        return salesOrderItemRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(salesOrderItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the salesOrderItems with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SalesOrderItemDTO> findAllWithEagerRelationships(Pageable pageable) {
        return salesOrderItemRepository.findAllWithEagerRelationships(pageable).map(salesOrderItemMapper::toDto);
    }

    /**
     * Get one salesOrderItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalesOrderItemDTO> findOne(Long id) {
        log.debug("Request to get SalesOrderItem : {}", id);
        return salesOrderItemRepository.findOneWithEagerRelationships(id).map(salesOrderItemMapper::toDto);
    }

    /**
     * Delete the salesOrderItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SalesOrderItem : {}", id);
        salesOrderItemRepository.deleteById(id);
    }
}
