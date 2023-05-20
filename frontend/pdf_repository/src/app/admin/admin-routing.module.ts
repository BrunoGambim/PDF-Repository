import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ReportedFilesComponent } from './reported-files/reported-files.component';
import { WaitingForValidationFilesComponent } from './waiting-for-validation-files/waiting-for-validation-files.component';
const routes: Routes = [
  {path: 'reportedPDFs', component: ReportedFilesComponent},
  {path: 'waitingForValidationPDFs', component: WaitingForValidationFilesComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
