import { element, by, ElementFinder } from 'protractor';

export class SalesOrderComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-sales-order div table .btn-danger'));
  title = element.all(by.css('jhi-sales-order div h2#page-heading span')).first();
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

export class SalesOrderUpdatePage {
  pageTitle = element(by.id('jhi-sales-order-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  salesOrderNumberInput = element(by.id('field_salesOrderNumber'));
  customerIdInput = element(by.id('field_customerId'));
  placedInput = element(by.id('field_placed'));
  cancelledInput = element(by.id('field_cancelled'));
  shippedInput = element(by.id('field_shipped'));
  completedInput = element(by.id('field_completed'));
  statusSelect = element(by.id('field_status'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setSalesOrderNumberInput(salesOrderNumber: string): Promise<void> {
    await this.salesOrderNumberInput.sendKeys(salesOrderNumber);
  }

  async getSalesOrderNumberInput(): Promise<string> {
    return await this.salesOrderNumberInput.getAttribute('value');
  }

  async setCustomerIdInput(customerId: string): Promise<void> {
    await this.customerIdInput.sendKeys(customerId);
  }

  async getCustomerIdInput(): Promise<string> {
    return await this.customerIdInput.getAttribute('value');
  }

  async setPlacedInput(placed: string): Promise<void> {
    await this.placedInput.sendKeys(placed);
  }

  async getPlacedInput(): Promise<string> {
    return await this.placedInput.getAttribute('value');
  }

  async setCancelledInput(cancelled: string): Promise<void> {
    await this.cancelledInput.sendKeys(cancelled);
  }

  async getCancelledInput(): Promise<string> {
    return await this.cancelledInput.getAttribute('value');
  }

  async setShippedInput(shipped: string): Promise<void> {
    await this.shippedInput.sendKeys(shipped);
  }

  async getShippedInput(): Promise<string> {
    return await this.shippedInput.getAttribute('value');
  }

  async setCompletedInput(completed: string): Promise<void> {
    await this.completedInput.sendKeys(completed);
  }

  async getCompletedInput(): Promise<string> {
    return await this.completedInput.getAttribute('value');
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

export class SalesOrderDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-salesOrder-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-salesOrder'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
