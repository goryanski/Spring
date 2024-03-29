import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    // if empty path -> go to home (so, home is a default page)
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    loadChildren: () => import('./pages/home/home.module').then(m => m.HomeModule)
  },
  {
    path: 'found-products/:name',
    loadChildren: () => import('./pages/found-products/found-products.module').then(m => m.FoundProductsModule)
  },
  {
    path: 'products-filter',
    loadChildren: () => import('./pages/products-filter/products-filter.module').then(m => m.ProductsFilterModule)
  },
  {
    path: 'registration',
    loadChildren: () => import('./pages/registration/registration.module').then(m => m.RegistrationModule)
  },
  {
    path: 'login',
    loadChildren: () => import('./pages/login/login.module').then(m => m.LoginModule)
  },
  {
    path: 'user-profile',
    loadChildren: () => import('./pages/user-profile/user-profile.module').then(m => m.UserProfileModule)
  },
  {
    path: 'admin-profile',
    loadChildren: () => import('./pages/admin-profile/admin-profile.module').then(m => m.AdminProfileModule)
  },
  {
    path: 'edit-product/:productId',
    loadChildren: () => import('./pages/edit-product/edit-product.module').then(m => m.EditProductModule)
  },
  {
    path: '**',
    loadChildren: () => import('./pages/not-found/not-found.module').then(m => m.NotFoundModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
