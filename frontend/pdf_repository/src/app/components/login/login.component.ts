import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { AUTHORIZATION_HEADER } from 'src/app/config/auth.config';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = ""
  password: string = ""

  constructor(private authService: AuthService, private router: Router){
  }

  login(){
    this.authService.authenticate(this.email, this.password).subscribe( res => {
      this.authService.successfulLogin(res.headers.get(AUTHORIZATION_HEADER))
      this.router.navigate([''])
    })
  }

  signup(){
    this.router.navigate(['saveNewClient'])
  }
}
