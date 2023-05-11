import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationState } from 'src/app/models/authentication_state';
import { AuthService } from 'src/app/services/auth/auth.service';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent {

  state: AuthenticationState

  constructor(private router: Router, private authService: AuthService){
    this.state = AuthenticationState.UNAUTHENTICATED
    authService.getAuthStateUpdates().subscribe(state => {
      this.state = state
    })
    authService.updateAuthState()
  }

  login(){
    this.router.navigate(['/login'])
  }

  logout(){
    this.authService.logout()
    this.router.navigate([''])
  }

  home(){
    this.router.navigate([''])
  }

  ownedPDFs(){
    this.router.navigate(['ownedPDFs'])
  }

  purchasedPDFs(){
    this.router.navigate(['purchasedPDFs'])
  }

  saveNewPDF(){
    this.router.navigate(['saveNewPDF'])
  }

  perfil(){
    this.router.navigate(['perfil'])
  }

  reportedPDFs(){
    this.router.navigate(['reportedPDFs'])
  }

  waitingForValidationPDFs(){
    this.router.navigate(['waitingForValidationPDFs'])
  }
}
