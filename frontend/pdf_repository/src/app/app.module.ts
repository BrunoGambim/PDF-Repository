import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HeaderComponent } from './components/header/header.component';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
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
import { MatDialogModule } from '@angular/material/dialog';
import { SaveNewClientComponent } from './components/save-new-client/save-new-client.component';
import { RecoverPasswordComponent } from './components/recover-password/recover-password.component';
import { UpdatePasswordComponent } from './components/update-password/update-password.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { ErrorInterceptorProvider } from './interceptors/error.interceptor';
import { UpdatePDFComponent } from './components/update-pdf/update-pdf.component';
import { CommonsModule } from './commons/commons.module';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    LoginComponent,
    SaveNewClientComponent,
    RecoverPasswordComponent,
    UpdatePasswordComponent,
    UpdatePDFComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatDividerModule,
    MatRadioModule,
    HttpClientModule,
    MatDialogModule,
    MatPaginatorModule,
    CommonsModule
  ],
  providers: [PdfService, AuthService, UserStorageService, AuthInterceptorProvider, ErrorInterceptorProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }
