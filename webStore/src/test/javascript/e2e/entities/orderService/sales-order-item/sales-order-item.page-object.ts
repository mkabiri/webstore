import { element, by, ElementFinder } from 'protractor';

export class SalesOrderItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-sales-order-item div table .btn-danger'));
  title = element.all(by.css('jhi-sales-order-item div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class SalesOrderItemUpdatePage {
  pageTitle = element(by.id('jhi-sales-order-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  nameInput = element(by.id('field_name'));
  skuInput = element(by.id('field_sku'));
  taxableInput = element(by.id('field_taxable'));
  grosWeightInput = element(by.id('field_grosWeight'));
  shippedInput = element(by.id('field_shipped'));
  deliveredInput = element(by.id('field_delivered'));
  statusSelect = element(by.id('field_status'));
  quantityInput = element(by.id('field_quantity'));
  unitPriceInput = element(by.id('field_unitPrice'));
  amountInput = element(by.id('field_amount'));

  salesOrderSelect = element(by.id('field_salesOrder'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setSkuInput(sku: string): Promise<void> {
    await this.skuInput.sendKeys(sku);
  }

  async getSkuInput(): Promise<string> {
    return await this.skuInput.getAttribute('value');
  }

  getTaxableInput(): ElementFinder {
    return this.taxableInput;
  }

  async setGrosWeightInput(grosWeight: string): Promise<void> {
    await this.grosWeightInput.sendKeys(grosWeight);
  }

  async getGrosWeightInput(): Promise<string> {
    return await this.grosWeightInput.getAttribute('value');
  }

  async setShippedInput(shipped: string): Promise<void> {
    await this.shippedInput.sendKeys(shipped);
  }

  async getShippedInput(): Promise<string> {
    return await this.shippedInput.getAttribute('value');
  }

  async setDeliveredInput(delivered: string): Promise<void> {
    await this.deliveredInput.sendKeys(delivered);
  }

  async getDeliveredInput(): Promise<string> {
    return await this.deliveredInput.getAttribute('value');
  }

  async setStatusSelect(status: string): Promise<void> {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect(): Promise<string> {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption(): Promise<void> {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }

  async setQuantityInput(quantity: string): Promise<void> {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput(): Promise<string> {
    return await this.quantityInput.getAttribute('value');
  }

  async setUnitPriceInput(unitPrice: string): Promise<void> {
    await this.unitPriceInput.sendKeys(unitPrice);
  }

  async getUnitPriceInput(): Promise<string> {
    return await this.unitPriceInput.getAttribute('value');
  }

  async setAmountInput(amount: string): Promise<void> {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput(): Promise<string> {
    return await this.amountInput.getAttribute('value');
  }

  async salesOrderSelectLastOption(): Promise<void> {
    await this.salesOrderSelect.all(by.tagName('option')).last().click();
  }

  async salesOrderSelectOption(option: string): Promise<void> {
    await this.salesOrderSelect.sendKeys(option);
  }

  getSalesOrderSelect(): ElementFinder {
    return this.salesOrderSelect;
  }

  async getSalesOrderSelectedOption(): Promise<string> {
    return await this.salesOrderSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class SalesOrderItemDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-salesOrderItem-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-salesOrderItem'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
