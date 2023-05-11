import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { AUTHORIZATION_HEADER } from 'src/app/config/auth.config';
import { Router } from '@angular/router';
import { ErrorDialogComponent } from '../error-dialog/error-dialog.component';
import { ERROR_MESSAGE } from 'src/app/config/error.config';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = ""
  password: string = ""

  constructor(private authService: AuthService, private router: Router,
    private dialog: MatDialog){
  }

  login(){
    if(!this.validateFormFields()){
      this.authService.authenticate(this.email, this.password).subscribe( res => {
        this.authService.successfulLogin(res.headers.get(AUTHORIZATION_HEADER))
        this.router.navigate([''])
      })
    }
  }

  signup(){
    this.router.navigate(['saveNewClient'])
  }

  private validateFormFields(){
    if(this.email == ""){
      this.dialog.open(ErrorDialogComponent,{data: ERROR_MESSAGE.NoEmailProvided})
      return false
    }else if(this.password == ""){
      this.dialog.open(ErrorDialogComponent,{data: ERROR_MESSAGE.NoPasswordProvided})
      return false
    }
    return true
  }
}
