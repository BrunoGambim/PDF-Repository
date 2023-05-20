import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PerfilComponent } from './perfil/perfil.component';
import { SaveNewPDFComponent } from './save-new-pdf/save-new-pdf.component';
import { PurchasedPDFsComponent } from './purchased-pdfs/purchased-pdfs.component';
import { OwnedPDFsComponent } from './owned-pdfs/owned-pdfs.component';

const routes: Routes = [
  {path: 'ownedPDFs', component: OwnedPDFsComponent},
  {path: 'purchasedPDFs', component: PurchasedPDFsComponent},
  {path: 'saveNewPDF', component: SaveNewPDFComponent},
  {path: 'perfil', component: PerfilComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientRoutingModule { }
