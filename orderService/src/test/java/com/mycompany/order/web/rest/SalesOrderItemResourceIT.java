package com.mycompany.order.web.rest;

import static com.mycompany.order.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.order.IntegrationTest;
import com.mycompany.order.domain.SalesOrderItem;
import com.mycompany.order.domain.enumeration.SalesOrderItemStatus;
import com.mycompany.order.repository.SalesOrderItemRepository;
import com.mycompany.order.service.SalesOrderItemService;
import com.mycompany.order.service.dto.SalesOrderItemDTO;
import com.mycompany.order.service.mapper.SalesOrderItemMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SalesOrderItemResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SalesOrderItemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SKU = "AAAAAAAAAA";
    private static final String UPDATED_SKU = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TAXABLE = false;
    private static final Boolean UPDATED_TAXABLE = true;

    private static final Double DEFAULT_GROS_WEIGHT = 1D;
    private static final Double UPDATED_GROS_WEIGHT = 2D;

    private static final LocalDate DEFAULT_SHIPPED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SHIPPED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DELIVERED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELIVERED = LocalDate.now(ZoneId.systemDefault());

    private static final SalesOrderItemStatus DEFAULT_STATUS = SalesOrderItemStatus.PENDING;
    private static final SalesOrderItemStatus UPDATED_STATUS = SalesOrderItemStatus.DELIVERED;

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/sales-order-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SalesOrderItemRepository salesOrderItemRepository;

    @Mock
    private SalesOrderItemRepository salesOrderItemRepositoryMock;

    @Autowired
    private SalesOrderItemMapper salesOrderItemMapper;

    @Mock
    private SalesOrderItemService salesOrderItemServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesOrderItemMockMvc;

    private SalesOrderItem salesOrderItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesOrderItem createEntity(EntityManager em) {
        SalesOrderItem salesOrderItem = new SalesOrderItem()
            .name(DEFAULT_NAME)
            .sku(DEFAULT_SKU)
            .taxable(DEFAULT_TAXABLE)
            .grosWeight(DEFAULT_GROS_WEIGHT)
            .shipped(DEFAULT_SHIPPED)
            .delivered(DEFAULT_DELIVERED)
            .status(DEFAULT_STATUS)
            .quantity(DEFAULT_QUANTITY)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .amount(DEFAULT_AMOUNT);
        return salesOrderItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesOrderItem createUpdatedEntity(EntityManager em) {
        SalesOrderItem salesOrderItem = new SalesOrderItem()
            .name(UPDATED_NAME)
            .sku(UPDATED_SKU)
            .taxable(UPDATED_TAXABLE)
            .grosWeight(UPDATED_GROS_WEIGHT)
            .shipped(UPDATED_SHIPPED)
            .delivered(UPDATED_DELIVERED)
            .status(UPDATED_STATUS)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .amount(UPDATED_AMOUNT);
        return salesOrderItem;
    }

    @BeforeEach
    public void initTest() {
        salesOrderItem = createEntity(em);
    }

    @Test
    @Transactional
    void createSalesOrderItem() throws Exception {
        int databaseSizeBeforeCreate = salesOrderItemRepository.findAll().size();
        // Create the SalesOrderItem
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemMapper.toDto(salesOrderItem);
        restSalesOrderItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SalesOrderItem in the database
        List<SalesOrderItem> salesOrderItemList = salesOrderItemRepository.findAll();
        assertThat(salesOrderItemList).hasSize(databaseSizeBeforeCreate + 1);
        SalesOrderItem testSalesOrderItem = salesOrderItemList.get(salesOrderItemList.size() - 1);
        assertThat(testSalesOrderItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSalesOrderItem.getSku()).isEqualTo(DEFAULT_SKU);
        assertThat(testSalesOrderItem.getTaxable()).isEqualTo(DEFAULT_TAXABLE);
        assertThat(testSalesOrderItem.getGrosWeight()).isEqualTo(DEFAULT_GROS_WEIGHT);
        assertThat(testSalesOrderItem.getShipped()).isEqualTo(DEFAULT_SHIPPED);
        assertThat(testSalesOrderItem.getDelivered()).isEqualTo(DEFAULT_DELIVERED);
        assertThat(testSalesOrderItem.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSalesOrderItem.getQuantity()).isEqualByComparingTo(DEFAULT_QUANTITY);
        assertThat(testSalesOrderItem.getUnitPrice()).isEqualByComparingTo(DEFAULT_UNIT_PRICE);
        assertThat(testSalesOrderItem.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void createSalesOrderItemWithExistingId() throws Exception {
        // Create the SalesOrderItem with an existing ID
        salesOrderItem.setId(1L);
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemMapper.toDto(salesOrderItem);

        int databaseSizeBeforeCreate = salesOrderItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesOrderItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesOrderItem in the database
        List<SalesOrderItem> salesOrderItemList = salesOrderItemRepository.findAll();
        assertThat(salesOrderItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSalesOrderItems() throws Exception {
        // Initialize the database
        salesOrderItemRepository.saveAndFlush(salesOrderItem);

        // Get all the salesOrderItemList
        restSalesOrderItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU)))
            .andExpect(jsonPath("$.[*].taxable").value(hasItem(DEFAULT_TAXABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].grosWeight").value(hasItem(DEFAULT_GROS_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].shipped").value(hasItem(DEFAULT_SHIPPED.toString())))
            .andExpect(jsonPath("$.[*].delivered").value(hasItem(DEFAULT_DELIVERED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(sameNumber(DEFAULT_QUANTITY))))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(sameNumber(DEFAULT_UNIT_PRICE))))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSalesOrderItemsWithEagerRelationshipsIsEnabled() throws Exception {
        when(salesOrderItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSalesOrderItemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(salesOrderItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSalesOrderItemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(salesOrderItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSalesOrderItemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(salesOrderItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getSalesOrderItem() throws Exception {
        // Initialize the database
        salesOrderItemRepository.saveAndFlush(salesOrderItem);

        // Get the salesOrderItem
        restSalesOrderItemMockMvc
            .perform(get(ENTITY_API_URL_ID, salesOrderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salesOrderItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.sku").value(DEFAULT_SKU))
            .andExpect(jsonPath("$.taxable").value(DEFAULT_TAXABLE.booleanValue()))
            .andExpect(jsonPath("$.grosWeight").value(DEFAULT_GROS_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.shipped").value(DEFAULT_SHIPPED.toString()))
            .andExpect(jsonPath("$.delivered").value(DEFAULT_DELIVERED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.quantity").value(sameNumber(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.unitPrice").value(sameNumber(DEFAULT_UNIT_PRICE)))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)));
    }

    @Test
    @Transactional
    void getNonExistingSalesOrderItem() throws Exception {
        // Get the salesOrderItem
        restSalesOrderItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSalesOrderItem() throws Exception {
        // Initialize the database
        salesOrderItemRepository.saveAndFlush(salesOrderItem);

        int databaseSizeBeforeUpdate = salesOrderItemRepository.findAll().size();

        // Update the salesOrderItem
        SalesOrderItem updatedSalesOrderItem = salesOrderItemRepository.findById(salesOrderItem.getId()).get();
        // Disconnect from session so that the updates on updatedSalesOrderItem are not directly saved in db
        em.detach(updatedSalesOrderItem);
        updatedSalesOrderItem
            .name(UPDATED_NAME)
            .sku(UPDATED_SKU)
            .taxable(UPDATED_TAXABLE)
            .grosWeight(UPDATED_GROS_WEIGHT)
            .shipped(UPDATED_SHIPPED)
            .delivered(UPDATED_DELIVERED)
            .status(UPDATED_STATUS)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .amount(UPDATED_AMOUNT);
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemMapper.toDto(updatedSalesOrderItem);

        restSalesOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salesOrderItemDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the SalesOrderItem in the database
        List<SalesOrderItem> salesOrderItemList = salesOrderItemRepository.findAll();
        assertThat(salesOrderItemList).hasSize(databaseSizeBeforeUpdate);
        SalesOrderItem testSalesOrderItem = salesOrderItemList.get(salesOrderItemList.size() - 1);
        assertThat(testSalesOrderItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSalesOrderItem.getSku()).isEqualTo(UPDATED_SKU);
        assertThat(testSalesOrderItem.getTaxable()).isEqualTo(UPDATED_TAXABLE);
        assertThat(testSalesOrderItem.getGrosWeight()).isEqualTo(UPDATED_GROS_WEIGHT);
        assertThat(testSalesOrderItem.getShipped()).isEqualTo(UPDATED_SHIPPED);
        assertThat(testSalesOrderItem.getDelivered()).isEqualTo(UPDATED_DELIVERED);
        assertThat(testSalesOrderItem.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSalesOrderItem.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
        assertThat(testSalesOrderItem.getUnitPrice()).isEqualByComparingTo(UPDATED_UNIT_PRICE);
        assertThat(testSalesOrderItem.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingSalesOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderItemRepository.findAll().size();
        salesOrderItem.setId(count.incrementAndGet());

        // Create the SalesOrderItem
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemMapper.toDto(salesOrderItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salesOrderItemDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesOrderItem in the database
        List<SalesOrderItem> salesOrderItemList = salesOrderItemRepository.findAll();
        assertThat(salesOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalesOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderItemRepository.findAll().size();
        salesOrderItem.setId(count.incrementAndGet());

        // Create the SalesOrderItem
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemMapper.toDto(salesOrderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesOrderItem in the database
        List<SalesOrderItem> salesOrderItemList = salesOrderItemRepository.findAll();
        assertThat(salesOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalesOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderItemRepository.findAll().size();
        salesOrderItem.setId(count.incrementAndGet());

        // Create the SalesOrderItem
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemMapper.toDto(salesOrderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesOrderItemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesOrderItem in the database
        List<SalesOrderItem> salesOrderItemList = salesOrderItemRepository.findAll();
        assertThat(salesOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalesOrderItemWithPatch() throws Exception {
        // Initialize the database
        salesOrderItemRepository.saveAndFlush(salesOrderItem);

        int databaseSizeBeforeUpdate = salesOrderItemRepository.findAll().size();

        // Update the salesOrderItem using partial update
        SalesOrderItem partialUpdatedSalesOrderItem = new SalesOrderItem();
        partialUpdatedSalesOrderItem.setId(salesOrderItem.getId());

        partialUpdatedSalesOrderItem
            .name(UPDATED_NAME)
            .shipped(UPDATED_SHIPPED)
            .delivered(UPDATED_DELIVERED)
            .unitPrice(UPDATED_UNIT_PRICE)
            .amount(UPDATED_AMOUNT);

        restSalesOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesOrderItem.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalesOrderItem))
            )
            .andExpect(status().isOk());

        // Validate the SalesOrderItem in the database
        List<SalesOrderItem> salesOrderItemList = salesOrderItemRepository.findAll();
        assertThat(salesOrderItemList).hasSize(databaseSizeBeforeUpdate);
        SalesOrderItem testSalesOrderItem = salesOrderItemList.get(salesOrderItemList.size() - 1);
        assertThat(testSalesOrderItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSalesOrderItem.getSku()).isEqualTo(DEFAULT_SKU);
        assertThat(testSalesOrderItem.getTaxable()).isEqualTo(DEFAULT_TAXABLE);
        assertThat(testSalesOrderItem.getGrosWeight()).isEqualTo(DEFAULT_GROS_WEIGHT);
        assertThat(testSalesOrderItem.getShipped()).isEqualTo(UPDATED_SHIPPED);
        assertThat(testSalesOrderItem.getDelivered()).isEqualTo(UPDATED_DELIVERED);
        assertThat(testSalesOrderItem.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSalesOrderItem.getQuantity()).isEqualByComparingTo(DEFAULT_QUANTITY);
        assertThat(testSalesOrderItem.getUnitPrice()).isEqualByComparingTo(UPDATED_UNIT_PRICE);
        assertThat(testSalesOrderItem.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdateSalesOrderItemWithPatch() throws Exception {
        // Initialize the database
        salesOrderItemRepository.saveAndFlush(salesOrderItem);

        int databaseSizeBeforeUpdate = salesOrderItemRepository.findAll().size();

        // Update the salesOrderItem using partial update
        SalesOrderItem partialUpdatedSalesOrderItem = new SalesOrderItem();
        partialUpdatedSalesOrderItem.setId(salesOrderItem.getId());

        partialUpdatedSalesOrderItem
            .name(UPDATED_NAME)
            .sku(UPDATED_SKU)
            .taxable(UPDATED_TAXABLE)
            .grosWeight(UPDATED_GROS_WEIGHT)
            .shipped(UPDATED_SHIPPED)
            .delivered(UPDATED_DELIVERED)
            .status(UPDATED_STATUS)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .amount(UPDATED_AMOUNT);

        restSalesOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesOrderItem.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalesOrderItem))
            )
            .andExpect(status().isOk());

        // Validate the SalesOrderItem in the database
        List<SalesOrderItem> salesOrderItemList = salesOrderItemRepository.findAll();
        assertThat(salesOrderItemList).hasSize(databaseSizeBeforeUpdate);
        SalesOrderItem testSalesOrderItem = salesOrderItemList.get(salesOrderItemList.size() - 1);
        assertThat(testSalesOrderItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSalesOrderItem.getSku()).isEqualTo(UPDATED_SKU);
        assertThat(testSalesOrderItem.getTaxable()).isEqualTo(UPDATED_TAXABLE);
        assertThat(testSalesOrderItem.getGrosWeight()).isEqualTo(UPDATED_GROS_WEIGHT);
        assertThat(testSalesOrderItem.getShipped()).isEqualTo(UPDATED_SHIPPED);
        assertThat(testSalesOrderItem.getDelivered()).isEqualTo(UPDATED_DELIVERED);
        assertThat(testSalesOrderItem.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSalesOrderItem.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
        assertThat(testSalesOrderItem.getUnitPrice()).isEqualByComparingTo(UPDATED_UNIT_PRICE);
        assertThat(testSalesOrderItem.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingSalesOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderItemRepository.findAll().size();
        salesOrderItem.setId(count.incrementAndGet());

        // Create the SalesOrderItem
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemMapper.toDto(salesOrderItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salesOrderItemDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesOrderItem in the database
        List<SalesOrderItem> salesOrderItemList = salesOrderItemRepository.findAll();
        assertThat(salesOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalesOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderItemRepository.findAll().size();
        salesOrderItem.setId(count.incrementAndGet());

        // Create the SalesOrderItem
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemMapper.toDto(salesOrderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesOrderItem in the database
        List<SalesOrderItem> salesOrderItemList = salesOrderItemRepository.findAll();
        assertThat(salesOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalesOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderItemRepository.findAll().size();
        salesOrderItem.setId(count.incrementAndGet());

        // Create the SalesOrderItem
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemMapper.toDto(salesOrderItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesOrderItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salesOrderItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesOrderItem in the database
        List<SalesOrderItem> salesOrderItemList = salesOrderItemRepository.findAll();
        assertThat(salesOrderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalesOrderItem() throws Exception {
        // Initialize the database
        salesOrderItemRepository.saveAndFlush(salesOrderItem);

        int databaseSizeBeforeDelete = salesOrderItemRepository.findAll().size();

        // Delete the salesOrderItem
        restSalesOrderItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, salesOrderItem.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SalesOrderItem> salesOrderItemList = salesOrderItemRepository.findAll();
        assertThat(salesOrderItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
