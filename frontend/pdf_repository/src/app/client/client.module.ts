import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';

import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatDialogModule } from '@angular/material/dialog';
import { MatPaginatorModule } from '@angular/material/paginator';
import { UpdateClientComponent } from './update-client/update-client.component';
import { PerfilComponent } from './perfil/perfil.component';
import { EvaluatePDFComponent } from './evaluate-pdf/evaluate-pdf.component';
import { SaveNewPDFComponent } from './save-new-pdf/save-new-pdf.component';
import { PurchasedPDFsComponent } from './purchased-pdfs/purchased-pdfs.component';
import { OwnedPDFsComponent } from './owned-pdfs/owned-pdfs.component';
import { ClientRoutingModule } from './client-routing.module';
import { CommonsModule } from '../commons/commons.module';



@NgModule({
  declarations: [
    OwnedPDFsComponent,
    PurchasedPDFsComponent,
    SaveNewPDFComponent,
    EvaluatePDFComponent,
    PerfilComponent,
    UpdateClientComponent,
  ],
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatDividerModule,
    MatDialogModule,
    MatPaginatorModule,
    ClientRoutingModule,
    CommonsModule
  ],
  exports:[
    OwnedPDFsComponent,
    PurchasedPDFsComponent,
    SaveNewPDFComponent,
    EvaluatePDFComponent,
    PerfilComponent,
    UpdateClientComponent,
  ]
})
export class ClientModule { }
