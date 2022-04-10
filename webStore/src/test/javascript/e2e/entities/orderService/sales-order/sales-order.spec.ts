import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { SalesOrderComponentsPage, SalesOrderDeleteDialog, SalesOrderUpdatePage } from './sales-order.page-object';

const expect = chai.expect;

describe('SalesOrder e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let salesOrderComponentsPage: SalesOrderComponentsPage;
  let salesOrderUpdatePage: SalesOrderUpdatePage;
  let salesOrderDeleteDialog: SalesOrderDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SalesOrders', async () => {
    await navBarPage.goToEntity('sales-order');
    salesOrderComponentsPage = new SalesOrderComponentsPage();
    await browser.wait(ec.visibilityOf(salesOrderComponentsPage.title), 5000);
    expect(await salesOrderComponentsPage.getTitle()).to.eq('webStoreApp.orderServiceSalesOrder.home.title');
    await browser.wait(ec.or(ec.visibilityOf(salesOrderComponentsPage.entities), ec.visibilityOf(salesOrderComponentsPage.noResult)), 1000);
  });

  it('should load create SalesOrder page', async () => {
    await salesOrderComponentsPage.clickOnCreateButton();
    salesOrderUpdatePage = new SalesOrderUpdatePage();
    expect(await salesOrderUpdatePage.getPageTitle()).to.eq('webStoreApp.orderServiceSalesOrder.home.createOrEditLabel');
    await salesOrderUpdatePage.cancel();
  });

  it('should create and save SalesOrders', async () => {
    const nbButtonsBeforeCreate = await salesOrderComponentsPage.countDeleteButtons();

    await salesOrderComponentsPage.clickOnCreateButton();

    await promise.all([
      salesOrderUpdatePage.setSalesOrderNumberInput('salesOrderNumber'),
      salesOrderUpdatePage.setCustomerIdInput('customerId'),
      salesOrderUpdatePage.setPlacedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      salesOrderUpdatePage.setCancelledInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      salesOrderUpdatePage.setShippedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      salesOrderUpdatePage.setCompletedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      salesOrderUpdatePage.statusSelectLastOption(),
    ]);

    await salesOrderUpdatePage.save();
    expect(await salesOrderUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await salesOrderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last SalesOrder', async () => {
    const nbButtonsBeforeDelete = await salesOrderComponentsPage.countDeleteButtons();
    await salesOrderComponentsPage.clickOnLastDeleteButton();

    salesOrderDeleteDialog = new SalesOrderDeleteDialog();
    expect(await salesOrderDeleteDialog.getDialogTitle()).to.eq('webStoreApp.orderServiceSalesOrder.delete.question');
    await salesOrderDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(salesOrderComponentsPage.title), 5000);

    expect(await salesOrderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
