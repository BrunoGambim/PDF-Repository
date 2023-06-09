import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HTTP_INTERCEPTORS,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { UserStorageService } from '../services/storage/user-storage.service';
import { AuthService } from '../services/auth/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../components/error-dialog/error-dialog.component';
import { ERROR_MESSAGE } from '../config/error.config';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private dialog: MatDialog) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request)
    .pipe(
      catchError((error: HttpErrorResponse) => {
        let errorObj = error;
        if(errorObj.error){
          errorObj = errorObj.error
        }
        if(!errorObj.status){
          errorObj = JSON.parse(error.error)
        }

        switch(errorObj.status){
          case 403:
            this.handle403()
          break;
          case 422:
            this.handle422(errorObj.message)
          break;
          default:
            this.handleDefault()
          break
        }

        return throwError(errorObj);
      }))
  }

  private handle403(){
    this.dialog.open(ErrorDialogComponent, { data : {data: {message: ERROR_MESSAGE.Error403, navigateToHomeOnClose:true}}, disableClose: true })
    this.authService.logout()
  }

  private handle422(message: string){
    this.dialog.open(ErrorDialogComponent, { data : {message: message, navigateToHomeOnClose:false}, disableClose: false })
  }

  private handleDefault(){
    this.dialog.open(ErrorDialogComponent, { data : {data: {message: ERROR_MESSAGE.DefaultError, navigateToHomeOnClose:true}}, disableClose: true })
  }
}

export const ErrorInterceptorProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: ErrorInterceptor,
  multi: true
}
