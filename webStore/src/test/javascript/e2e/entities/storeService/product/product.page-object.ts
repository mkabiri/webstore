import { element, by, ElementFinder } from 'protractor';

export class ProductComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product div table .btn-danger'));
  title = element.all(by.css('jhi-product div h2#page-heading span')).first();
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

export class ProductUpdatePage {
  pageTitle = element(by.id('jhi-product-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  nameInput = element(by.id('field_name'));
  skuInput = element(by.id('field_sku'));
  descriptionInput = element(by.id('field_description'));
  srpInput = element(by.id('field_srp'));
  taxableInput = element(by.id('field_taxable'));
  salesUnitSelect = element(by.id('field_salesUnit'));
  salesQuantityInput = element(by.id('field_salesQuantity'));
  statusSelect = element(by.id('field_status'));
  grosWeightInput = element(by.id('field_grosWeight'));
  netWeightInput = element(by.id('field_netWeight'));
  lengthInput = element(by.id('field_length'));
  widthInput = element(by.id('field_width'));
  heightInput = element(by.id('field_height'));

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

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setSrpInput(srp: string): Promise<void> {
    await this.srpInput.sendKeys(srp);
  }

  async getSrpInput(): Promise<string> {
    return await this.srpInput.getAttribute('value');
  }

  getTaxableInput(): ElementFinder {
    return this.taxableInput;
  }

  async setSalesUnitSelect(salesUnit: string): Promise<void> {
    await this.salesUnitSelect.sendKeys(salesUnit);
  }

  async getSalesUnitSelect(): Promise<string> {
    return await this.salesUnitSelect.element(by.css('option:checked')).getText();
  }

  async salesUnitSelectLastOption(): Promise<void> {
    await this.salesUnitSelect.all(by.tagName('option')).last().click();
  }

  async setSalesQuantityInput(salesQuantity: string): Promise<void> {
    await this.salesQuantityInput.sendKeys(salesQuantity);
  }

  async getSalesQuantityInput(): Promise<string> {
    return await this.salesQuantityInput.getAttribute('value');
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

  async setGrosWeightInput(grosWeight: string): Promise<void> {
    await this.grosWeightInput.sendKeys(grosWeight);
  }

  async getGrosWeightInput(): Promise<string> {
    return await this.grosWeightInput.getAttribute('value');
  }

  async setNetWeightInput(netWeight: string): Promise<void> {
    await this.netWeightInput.sendKeys(netWeight);
  }

  async getNetWeightInput(): Promise<string> {
    return await this.netWeightInput.getAttribute('value');
  }

  async setLengthInput(length: string): Promise<void> {
    await this.lengthInput.sendKeys(length);
  }

  async getLengthInput(): Promise<string> {
    return await this.lengthInput.getAttribute('value');
  }

  async setWidthInput(width: string): Promise<void> {
    await this.widthInput.sendKeys(width);
  }

  async getWidthInput(): Promise<string> {
    return await this.widthInput.getAttribute('value');
  }

  async setHeightInput(height: string): Promise<void> {
    await this.heightInput.sendKeys(height);
  }

  async getHeightInput(): Promise<string> {
    return await this.heightInput.getAttribute('value');
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

export class ProductDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-product-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-product'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
