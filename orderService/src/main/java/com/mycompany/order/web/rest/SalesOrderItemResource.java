package com.mycompany.order.web.rest;

import com.mycompany.order.repository.SalesOrderItemRepository;
import com.mycompany.order.service.SalesOrderItemService;
import com.mycompany.order.service.dto.SalesOrderItemDTO;
import com.mycompany.order.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.order.domain.SalesOrderItem}.
 */
@RestController
@RequestMapping("/api")
public class SalesOrderItemResource {

    private final Logger log = LoggerFactory.getLogger(SalesOrderItemResource.class);

    private static final String ENTITY_NAME = "orderServiceSalesOrderItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesOrderItemService salesOrderItemService;

    private final SalesOrderItemRepository salesOrderItemRepository;

    public SalesOrderItemResource(SalesOrderItemService salesOrderItemService, SalesOrderItemRepository salesOrderItemRepository) {
        this.salesOrderItemService = salesOrderItemService;
        this.salesOrderItemRepository = salesOrderItemRepository;
    }

    /**
     * {@code POST  /sales-order-items} : Create a new salesOrderItem.
     *
     * @param salesOrderItemDTO the salesOrderItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesOrderItemDTO, or with status {@code 400 (Bad Request)} if the salesOrderItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sales-order-items")
    public ResponseEntity<SalesOrderItemDTO> createSalesOrderItem(@RequestBody SalesOrderItemDTO salesOrderItemDTO)
        throws URISyntaxException {
        log.debug("REST request to save SalesOrderItem : {}", salesOrderItemDTO);
        if (salesOrderItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new salesOrderItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalesOrderItemDTO result = salesOrderItemService.save(salesOrderItemDTO);
        return ResponseEntity
            .created(new URI("/api/sales-order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sales-order-items/:id} : Updates an existing salesOrderItem.
     *
     * @param id the id of the salesOrderItemDTO to save.
     * @param salesOrderItemDTO the salesOrderItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesOrderItemDTO,
     * or with status {@code 400 (Bad Request)} if the salesOrderItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesOrderItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sales-order-items/{id}")
    public ResponseEntity<SalesOrderItemDTO> updateSalesOrderItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalesOrderItemDTO salesOrderItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SalesOrderItem : {}, {}", id, salesOrderItemDTO);
        if (salesOrderItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesOrderItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesOrderItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SalesOrderItemDTO result = salesOrderItemService.update(salesOrderItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salesOrderItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sales-order-items/:id} : Partial updates given fields of an existing salesOrderItem, field will ignore if it is null
     *
     * @param id the id of the salesOrderItemDTO to save.
     * @param salesOrderItemDTO the salesOrderItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesOrderItemDTO,
     * or with status {@code 400 (Bad Request)} if the salesOrderItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the salesOrderItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the salesOrderItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sales-order-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SalesOrderItemDTO> partialUpdateSalesOrderItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalesOrderItemDTO salesOrderItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SalesOrderItem partially : {}, {}", id, salesOrderItemDTO);
        if (salesOrderItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesOrderItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesOrderItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SalesOrderItemDTO> result = salesOrderItemService.partialUpdate(salesOrderItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salesOrderItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sales-order-items} : get all the salesOrderItems.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesOrderItems in body.
     */
    @GetMapping("/sales-order-items")
    public List<SalesOrderItemDTO> getAllSalesOrderItems(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all SalesOrderItems");
        return salesOrderItemService.findAll();
    }

    /**
     * {@code GET  /sales-order-items/:id} : get the "id" salesOrderItem.
     *
     * @param id the id of the salesOrderItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesOrderItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sales-order-items/{id}")
    public ResponseEntity<SalesOrderItemDTO> getSalesOrderItem(@PathVariable Long id) {
        log.debug("REST request to get SalesOrderItem : {}", id);
        Optional<SalesOrderItemDTO> salesOrderItemDTO = salesOrderItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salesOrderItemDTO);
    }

    /**
     * {@code DELETE  /sales-order-items/:id} : delete the "id" salesOrderItem.
     *
     * @param id the id of the salesOrderItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sales-order-items/{id}")
    public ResponseEntity<Void> deleteSalesOrderItem(@PathVariable Long id) {
        log.debug("REST request to delete SalesOrderItem : {}", id);
        salesOrderItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
