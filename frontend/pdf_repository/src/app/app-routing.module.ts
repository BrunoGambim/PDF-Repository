import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { OwnedPDFsComponent } from './components/owned-pdfs/owned-pdfs.component';
import { PurchasedPDFsComponent } from './components/purchased-pdfs/purchased-pdfs.component';
import { SaveNewPDFComponent } from './components/save-new-pdf/save-new-pdf.component';
import { UpdatePDFComponent } from './components/update-pdf/update-pdf.component';
import { PerfilComponent } from './components/perfil/perfil.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'ownedPDFs', component: OwnedPDFsComponent},
  {path: 'purchasedPDFs', component: PurchasedPDFsComponent},
  {path: 'saveNewPDF', component: SaveNewPDFComponent},
  {path: 'updatePDF', component: UpdatePDFComponent},
  {path: 'perfil', component: PerfilComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
