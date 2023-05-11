import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { OwnedPDFsComponent } from './components/owned-pdfs/owned-pdfs.component';
import { PurchasedPDFsComponent } from './components/purchased-pdfs/purchased-pdfs.component';
import { SaveNewPDFComponent } from './components/save-new-pdf/save-new-pdf.component';
import { UpdatePDFComponent } from './components/update-pdf/update-pdf.component';
import { PerfilComponent } from './components/perfil/perfil.component';
import { SaveNewClientComponent } from './components/save-new-client/save-new-client.component';
import { RecoverPasswordComponent } from './components/recover-password/recover-password.component';
import { UpdatePasswordComponent } from './components/update-password/update-password.component';
import { ReportedFilesComponent } from './components/reported-files/reported-files.component';
import { WaitingForValidationFilesComponent } from './components/waiting-for-validation-files/waiting-for-validation-files.component';
import { ClientGuard } from './guards/client.guard';
import { AuthenticatedGuard } from './guards/authenticated.guard';
import { AdminGuard } from './guards/admin.guard';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'ownedPDFs', component: OwnedPDFsComponent, canActivate: [ClientGuard]},
  {path: 'purchasedPDFs', component: PurchasedPDFsComponent, canActivate: [ClientGuard]},
  {path: 'saveNewPDF', component: SaveNewPDFComponent, canActivate: [ClientGuard]},
  {path: 'updatePDF', component: UpdatePDFComponent, canActivate: [AuthenticatedGuard]},
  {path: 'perfil', component: PerfilComponent, canActivate: [ClientGuard]},
  {path: 'saveNewClient', component: SaveNewClientComponent},
  {path: 'recoverPassword', component: RecoverPasswordComponent},
  {path: 'updatePassword', component: UpdatePasswordComponent},
  {path: 'reportedPDFs', component: ReportedFilesComponent, canActivate: [AdminGuard]},
  {path: 'waitingForValidationPDFs', component: WaitingForValidationFilesComponent, canActivate: [AdminGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
