import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { OwnedPDFsComponent } from './components/owned-pdfs/owned-pdfs.component';
import { PurchasedPDFsComponent } from './components/purchased-pdfs/purchased-pdfs.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'ownedPDFs', component: OwnedPDFsComponent},
  {path: 'purchasedPDFs', component: PurchasedPDFsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
