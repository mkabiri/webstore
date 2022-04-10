package com.mycompany.order.web.rest;

import com.mycompany.order.repository.SalesOrderRepository;
import com.mycompany.order.service.SalesOrderService;
import com.mycompany.order.service.dto.SalesOrderDTO;
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
 * REST controller for managing {@link com.mycompany.order.domain.SalesOrder}.
 */
@RestController
@RequestMapping("/api")
public class SalesOrderResource {

    private final Logger log = LoggerFactory.getLogger(SalesOrderResource.class);

    private static final String ENTITY_NAME = "orderServiceSalesOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesOrderService salesOrderService;

    private final SalesOrderRepository salesOrderRepository;

    public SalesOrderResource(SalesOrderService salesOrderService, SalesOrderRepository salesOrderRepository) {
        this.salesOrderService = salesOrderService;
        this.salesOrderRepository = salesOrderRepository;
    }

    /**
     * {@code POST  /sales-orders} : Create a new salesOrder.
     *
     * @param salesOrderDTO the salesOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesOrderDTO, or with status {@code 400 (Bad Request)} if the salesOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sales-orders")
    public ResponseEntity<SalesOrderDTO> createSalesOrder(@RequestBody SalesOrderDTO salesOrderDTO) throws URISyntaxException {
        log.debug("REST request to save SalesOrder : {}", salesOrderDTO);
        if (salesOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new salesOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalesOrderDTO result = salesOrderService.save(salesOrderDTO);
        return ResponseEntity
            .created(new URI("/api/sales-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sales-orders/:id} : Updates an existing salesOrder.
     *
     * @param id the id of the salesOrderDTO to save.
     * @param salesOrderDTO the salesOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesOrderDTO,
     * or with status {@code 400 (Bad Request)} if the salesOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sales-orders/{id}")
    public ResponseEntity<SalesOrderDTO> updateSalesOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalesOrderDTO salesOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SalesOrder : {}, {}", id, salesOrderDTO);
        if (salesOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SalesOrderDTO result = salesOrderService.update(salesOrderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salesOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sales-orders/:id} : Partial updates given fields of an existing salesOrder, field will ignore if it is null
     *
     * @param id the id of the salesOrderDTO to save.
     * @param salesOrderDTO the salesOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesOrderDTO,
     * or with status {@code 400 (Bad Request)} if the salesOrderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the salesOrderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the salesOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sales-orders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SalesOrderDTO> partialUpdateSalesOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalesOrderDTO salesOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SalesOrder partially : {}, {}", id, salesOrderDTO);
        if (salesOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SalesOrderDTO> result = salesOrderService.partialUpdate(salesOrderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salesOrderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sales-orders} : get all the salesOrders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesOrders in body.
     */
    @GetMapping("/sales-orders")
    public List<SalesOrderDTO> getAllSalesOrders() {
        log.debug("REST request to get all SalesOrders");
        return salesOrderService.findAll();
    }

    /**
     * {@code GET  /sales-orders/:id} : get the "id" salesOrder.
     *
     * @param id the id of the salesOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sales-orders/{id}")
    public ResponseEntity<SalesOrderDTO> getSalesOrder(@PathVariable Long id) {
        log.debug("REST request to get SalesOrder : {}", id);
        Optional<SalesOrderDTO> salesOrderDTO = salesOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salesOrderDTO);
    }

    /**
     * {@code DELETE  /sales-orders/:id} : delete the "id" salesOrder.
     *
     * @param id the id of the salesOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sales-orders/{id}")
    public ResponseEntity<Void> deleteSalesOrder(@PathVariable Long id) {
        log.debug("REST request to delete SalesOrder : {}", id);
        salesOrderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
