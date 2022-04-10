package com.mycompany.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.order.IntegrationTest;
import com.mycompany.order.domain.SalesOrder;
import com.mycompany.order.domain.enumeration.SalesOrderStatus;
import com.mycompany.order.repository.SalesOrderRepository;
import com.mycompany.order.service.dto.SalesOrderDTO;
import com.mycompany.order.service.mapper.SalesOrderMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SalesOrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SalesOrderResourceIT {

    private static final String DEFAULT_SALES_ORDER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SALES_ORDER_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_PLACED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PLACED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CANCELLED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CANCELLED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SHIPPED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SHIPPED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_COMPLETED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COMPLETED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final SalesOrderStatus DEFAULT_STATUS = SalesOrderStatus.PENDING;
    private static final SalesOrderStatus UPDATED_STATUS = SalesOrderStatus.CANCELLED;

    private static final String ENTITY_API_URL = "/api/sales-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesOrderMockMvc;

    private SalesOrder salesOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesOrder createEntity(EntityManager em) {
        SalesOrder salesOrder = new SalesOrder()
            .salesOrderNumber(DEFAULT_SALES_ORDER_NUMBER)
            .customerId(DEFAULT_CUSTOMER_ID)
            .placed(DEFAULT_PLACED)
            .cancelled(DEFAULT_CANCELLED)
            .shipped(DEFAULT_SHIPPED)
            .completed(DEFAULT_COMPLETED)
            .status(DEFAULT_STATUS);
        return salesOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesOrder createUpdatedEntity(EntityManager em) {
        SalesOrder salesOrder = new SalesOrder()
            .salesOrderNumber(UPDATED_SALES_ORDER_NUMBER)
            .customerId(UPDATED_CUSTOMER_ID)
            .placed(UPDATED_PLACED)
            .cancelled(UPDATED_CANCELLED)
            .shipped(UPDATED_SHIPPED)
            .completed(UPDATED_COMPLETED)
            .status(UPDATED_STATUS);
        return salesOrder;
    }

    @BeforeEach
    public void initTest() {
        salesOrder = createEntity(em);
    }

    @Test
    @Transactional
    void createSalesOrder() throws Exception {
        int databaseSizeBeforeCreate = salesOrderRepository.findAll().size();
        // Create the SalesOrder
        SalesOrderDTO salesOrderDTO = salesOrderMapper.toDto(salesOrder);
        restSalesOrderMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeCreate + 1);
        SalesOrder testSalesOrder = salesOrderList.get(salesOrderList.size() - 1);
        assertThat(testSalesOrder.getSalesOrderNumber()).isEqualTo(DEFAULT_SALES_ORDER_NUMBER);
        assertThat(testSalesOrder.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testSalesOrder.getPlaced()).isEqualTo(DEFAULT_PLACED);
        assertThat(testSalesOrder.getCancelled()).isEqualTo(DEFAULT_CANCELLED);
        assertThat(testSalesOrder.getShipped()).isEqualTo(DEFAULT_SHIPPED);
        assertThat(testSalesOrder.getCompleted()).isEqualTo(DEFAULT_COMPLETED);
        assertThat(testSalesOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createSalesOrderWithExistingId() throws Exception {
        // Create the SalesOrder with an existing ID
        salesOrder.setId(1L);
        SalesOrderDTO salesOrderDTO = salesOrderMapper.toDto(salesOrder);

        int databaseSizeBeforeCreate = salesOrderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesOrderMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSalesOrders() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        // Get all the salesOrderList
        restSalesOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].salesOrderNumber").value(hasItem(DEFAULT_SALES_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].placed").value(hasItem(DEFAULT_PLACED.toString())))
            .andExpect(jsonPath("$.[*].cancelled").value(hasItem(DEFAULT_CANCELLED.toString())))
            .andExpect(jsonPath("$.[*].shipped").value(hasItem(DEFAULT_SHIPPED.toString())))
            .andExpect(jsonPath("$.[*].completed").value(hasItem(DEFAULT_COMPLETED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getSalesOrder() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        // Get the salesOrder
        restSalesOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, salesOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salesOrder.getId().intValue()))
            .andExpect(jsonPath("$.salesOrderNumber").value(DEFAULT_SALES_ORDER_NUMBER))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.placed").value(DEFAULT_PLACED.toString()))
            .andExpect(jsonPath("$.cancelled").value(DEFAULT_CANCELLED.toString()))
            .andExpect(jsonPath("$.shipped").value(DEFAULT_SHIPPED.toString()))
            .andExpect(jsonPath("$.completed").value(DEFAULT_COMPLETED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSalesOrder() throws Exception {
        // Get the salesOrder
        restSalesOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSalesOrder() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();

        // Update the salesOrder
        SalesOrder updatedSalesOrder = salesOrderRepository.findById(salesOrder.getId()).get();
        // Disconnect from session so that the updates on updatedSalesOrder are not directly saved in db
        em.detach(updatedSalesOrder);
        updatedSalesOrder
            .salesOrderNumber(UPDATED_SALES_ORDER_NUMBER)
            .customerId(UPDATED_CUSTOMER_ID)
            .placed(UPDATED_PLACED)
            .cancelled(UPDATED_CANCELLED)
            .shipped(UPDATED_SHIPPED)
            .completed(UPDATED_COMPLETED)
            .status(UPDATED_STATUS);
        SalesOrderDTO salesOrderDTO = salesOrderMapper.toDto(updatedSalesOrder);

        restSalesOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salesOrderDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO))
            )
            .andExpect(status().isOk());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeUpdate);
        SalesOrder testSalesOrder = salesOrderList.get(salesOrderList.size() - 1);
        assertThat(testSalesOrder.getSalesOrderNumber()).isEqualTo(UPDATED_SALES_ORDER_NUMBER);
        assertThat(testSalesOrder.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testSalesOrder.getPlaced()).isEqualTo(UPDATED_PLACED);
        assertThat(testSalesOrder.getCancelled()).isEqualTo(UPDATED_CANCELLED);
        assertThat(testSalesOrder.getShipped()).isEqualTo(UPDATED_SHIPPED);
        assertThat(testSalesOrder.getCompleted()).isEqualTo(UPDATED_COMPLETED);
        assertThat(testSalesOrder.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingSalesOrder() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();
        salesOrder.setId(count.incrementAndGet());

        // Create the SalesOrder
        SalesOrderDTO salesOrderDTO = salesOrderMapper.toDto(salesOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salesOrderDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalesOrder() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();
        salesOrder.setId(count.incrementAndGet());

        // Create the SalesOrder
        SalesOrderDTO salesOrderDTO = salesOrderMapper.toDto(salesOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalesOrder() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();
        salesOrder.setId(count.incrementAndGet());

        // Create the SalesOrder
        SalesOrderDTO salesOrderDTO = salesOrderMapper.toDto(salesOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesOrderMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalesOrderWithPatch() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();

        // Update the salesOrder using partial update
        SalesOrder partialUpdatedSalesOrder = new SalesOrder();
        partialUpdatedSalesOrder.setId(salesOrder.getId());

        partialUpdatedSalesOrder.placed(UPDATED_PLACED).shipped(UPDATED_SHIPPED).completed(UPDATED_COMPLETED);

        restSalesOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesOrder.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalesOrder))
            )
            .andExpect(status().isOk());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeUpdate);
        SalesOrder testSalesOrder = salesOrderList.get(salesOrderList.size() - 1);
        assertThat(testSalesOrder.getSalesOrderNumber()).isEqualTo(DEFAULT_SALES_ORDER_NUMBER);
        assertThat(testSalesOrder.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testSalesOrder.getPlaced()).isEqualTo(UPDATED_PLACED);
        assertThat(testSalesOrder.getCancelled()).isEqualTo(DEFAULT_CANCELLED);
        assertThat(testSalesOrder.getShipped()).isEqualTo(UPDATED_SHIPPED);
        assertThat(testSalesOrder.getCompleted()).isEqualTo(UPDATED_COMPLETED);
        assertThat(testSalesOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateSalesOrderWithPatch() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();

        // Update the salesOrder using partial update
        SalesOrder partialUpdatedSalesOrder = new SalesOrder();
        partialUpdatedSalesOrder.setId(salesOrder.getId());

        partialUpdatedSalesOrder
            .salesOrderNumber(UPDATED_SALES_ORDER_NUMBER)
            .customerId(UPDATED_CUSTOMER_ID)
            .placed(UPDATED_PLACED)
            .cancelled(UPDATED_CANCELLED)
            .shipped(UPDATED_SHIPPED)
            .completed(UPDATED_COMPLETED)
            .status(UPDATED_STATUS);

        restSalesOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesOrder.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalesOrder))
            )
            .andExpect(status().isOk());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeUpdate);
        SalesOrder testSalesOrder = salesOrderList.get(salesOrderList.size() - 1);
        assertThat(testSalesOrder.getSalesOrderNumber()).isEqualTo(UPDATED_SALES_ORDER_NUMBER);
        assertThat(testSalesOrder.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testSalesOrder.getPlaced()).isEqualTo(UPDATED_PLACED);
        assertThat(testSalesOrder.getCancelled()).isEqualTo(UPDATED_CANCELLED);
        assertThat(testSalesOrder.getShipped()).isEqualTo(UPDATED_SHIPPED);
        assertThat(testSalesOrder.getCompleted()).isEqualTo(UPDATED_COMPLETED);
        assertThat(testSalesOrder.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingSalesOrder() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();
        salesOrder.setId(count.incrementAndGet());

        // Create the SalesOrder
        SalesOrderDTO salesOrderDTO = salesOrderMapper.toDto(salesOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salesOrderDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalesOrder() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();
        salesOrder.setId(count.incrementAndGet());

        // Create the SalesOrder
        SalesOrderDTO salesOrderDTO = salesOrderMapper.toDto(salesOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalesOrder() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();
        salesOrder.setId(count.incrementAndGet());

        // Create the SalesOrder
        SalesOrderDTO salesOrderDTO = salesOrderMapper.toDto(salesOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesOrderMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalesOrder() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        int databaseSizeBeforeDelete = salesOrderRepository.findAll().size();

        // Delete the salesOrder
        restSalesOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, salesOrder.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
