import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { AUTHORIZATION_HEADER } from 'src/app/config/auth.config';
import { Router } from '@angular/router';
import { ErrorDialogComponent } from '../error-dialog/error-dialog.component';
import { ERROR_MESSAGE } from 'src/app/config/error.config';
import { MatDialog } from '@angular/material/dialog';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm = new FormGroup({
    password: new FormControl('', [ Validators.required ]),
    email: new FormControl('', [ Validators.required ]),
  });

  constructor(private authService: AuthService, private router: Router,
    private dialog: MatDialog){
  }

  login(){
    this.authService.authenticate(this.loginForm.get("email")?.value as string, this.loginForm.get("password")?.value as string).subscribe({next: (res) => {
      this.authService.successfulLogin(res.headers.get(AUTHORIZATION_HEADER))
      this.router.navigate([''])
    }, error: () => {}})
  }

  signup(){
    this.router.navigate(['saveNewClient'])
  }
}
