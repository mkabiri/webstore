import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { PhotoComponentsPage, PhotoDeleteDialog, PhotoUpdatePage } from './photo.page-object';
import path from 'path';

const expect = chai.expect;

describe('Photo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let photoComponentsPage: PhotoComponentsPage;
  let photoUpdatePage: PhotoUpdatePage;
  let photoDeleteDialog: PhotoDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Photos', async () => {
    await navBarPage.goToEntity('photo');
    photoComponentsPage = new PhotoComponentsPage();
    await browser.wait(ec.visibilityOf(photoComponentsPage.title), 5000);
    expect(await photoComponentsPage.getTitle()).to.eq('webStoreApp.storeServicePhoto.home.title');
    await browser.wait(ec.or(ec.visibilityOf(photoComponentsPage.entities), ec.visibilityOf(photoComponentsPage.noResult)), 1000);
  });

  it('should load create Photo page', async () => {
    await photoComponentsPage.clickOnCreateButton();
    photoUpdatePage = new PhotoUpdatePage();
    expect(await photoUpdatePage.getPageTitle()).to.eq('webStoreApp.storeServicePhoto.home.createOrEditLabel');
    await photoUpdatePage.cancel();
  });

  it('should create and save Photos', async () => {
    const nbButtonsBeforeCreate = await photoComponentsPage.countDeleteButtons();

    await photoComponentsPage.clickOnCreateButton();

    await promise.all([photoUpdatePage.setPhotoInput(absolutePath), photoUpdatePage.productSelectLastOption()]);

    await photoUpdatePage.save();
    expect(await photoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await photoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Photo', async () => {
    const nbButtonsBeforeDelete = await photoComponentsPage.countDeleteButtons();
    await photoComponentsPage.clickOnLastDeleteButton();

    photoDeleteDialog = new PhotoDeleteDialog();
    expect(await photoDeleteDialog.getDialogTitle()).to.eq('webStoreApp.storeServicePhoto.delete.question');
    await photoDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(photoComponentsPage.title), 5000);

    expect(await photoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
