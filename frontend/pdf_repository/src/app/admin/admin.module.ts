import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WaitingForValidationFilesComponent } from './waiting-for-validation-files/waiting-for-validation-files.component';
import { ReportedFilesComponent } from './reported-files/reported-files.component';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { AdminRoutingModule } from './admin-routing.module';



@NgModule({
  declarations: [
    ReportedFilesComponent,
    WaitingForValidationFilesComponent,
  ],
  imports: [
    CommonModule,
    MatCardModule,
    MatDividerModule,
    MatPaginatorModule,
    MatIconModule,
    MatButtonModule,
    AdminRoutingModule
  ],
  exports:[
    ReportedFilesComponent,
    WaitingForValidationFilesComponent,
  ]
})
export class AdminModule { }
