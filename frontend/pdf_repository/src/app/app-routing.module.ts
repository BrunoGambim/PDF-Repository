import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticatedGuard } from './guards/authenticated.guard';
import { AdminGuard } from './guards/admin.guard';
import { ClientGuard } from './guards/client.guard';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { UpdatePDFComponent } from './components/update-pdf/update-pdf.component';
import { SaveNewClientComponent } from './components/save-new-client/save-new-client.component';
import { RecoverPasswordComponent } from './components/recover-password/recover-password.component';
import { UpdatePasswordComponent } from './components/update-password/update-password.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'admins', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule), canActivate: [AdminGuard]},
  {path: 'clients', loadChildren: () => import('./client/client.module').then(m => m.ClientModule) , canActivate: [ClientGuard]},
  {path: 'login', component: LoginComponent},
  {path: 'updatePDF', component: UpdatePDFComponent, canActivate: [AuthenticatedGuard]},
  {path: 'saveNewClient', component: SaveNewClientComponent},
  {path: 'recoverPassword', component: RecoverPasswordComponent},
  {path: 'updatePassword', component: UpdatePasswordComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
