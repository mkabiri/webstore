import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'sales-order-item',
        data: { pageTitle: 'webStoreApp.orderServiceSalesOrderItem.home.title' },
        loadChildren: () => import('./orderService/sales-order-item/sales-order-item.module').then(m => m.OrderServiceSalesOrderItemModule),
      },
      {
        path: 'photo',
        data: { pageTitle: 'webStoreApp.storeServicePhoto.home.title' },
        loadChildren: () => import('./storeService/photo/photo.module').then(m => m.StoreServicePhotoModule),
      },
      {
        path: 'customer',
        data: { pageTitle: 'webStoreApp.customer.home.title' },
        loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule),
      },
      {
        path: 'product',
        data: { pageTitle: 'webStoreApp.storeServiceProduct.home.title' },
        loadChildren: () => import('./storeService/product/product.module').then(m => m.StoreServiceProductModule),
      },
      {
        path: 'sales-order',
        data: { pageTitle: 'webStoreApp.orderServiceSalesOrder.home.title' },
        loadChildren: () => import('./orderService/sales-order/sales-order.module').then(m => m.OrderServiceSalesOrderModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
