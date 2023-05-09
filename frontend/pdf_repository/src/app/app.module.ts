import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HeaderComponent } from './components/header/header.component';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { HomeComponent } from './components/home/home.component';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatRadioModule } from '@angular/material/radio';
import { HttpClientModule } from '@angular/common/http';
import { PdfService } from './services/pdf/pdf.service';
import { AuthService } from './services/auth/auth.service';
import { LoginComponent } from './components/login/login.component';
import { UserStorageService } from './services/storage/user-storage.service';
import { AuthInterceptorProvider } from './interceptors/auth-interceptor';
import { OwnedPDFsComponent } from './components/owned-pdfs/owned-pdfs.component';
import { PurchasedPDFsComponent } from './components/purchased-pdfs/purchased-pdfs.component';
import { SaveNewPDFComponent } from './components/save-new-pdf/save-new-pdf.component';
import { UpdatePDFComponent } from './components/update-pdf/update-pdf.component';
import { EvaluatePDFComponent } from './components/evaluate-pdf/evaluate-pdf.component';
import { MatDialogModule } from '@angular/material/dialog';
import { PerfilComponent } from './components/perfil/perfil.component';
import { UpdateClientComponent } from './components/update-client/update-client.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    LoginComponent,
    OwnedPDFsComponent,
    PurchasedPDFsComponent,
    SaveNewPDFComponent,
    UpdatePDFComponent,
    EvaluatePDFComponent,
    PerfilComponent,
    UpdateClientComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatDividerModule,
    MatRadioModule,
    HttpClientModule,
    MatDialogModule
  ],
  providers: [PdfService, AuthService, UserStorageService, AuthInterceptorProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }
