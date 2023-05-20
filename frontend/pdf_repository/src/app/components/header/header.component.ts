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
    authService.getAuthStateUpdates().subscribe({next: (res) => {
      this.state = res
    }, error: () => {}})
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
    this.router.navigate(['clients/ownedPDFs'])
  }

  purchasedPDFs(){
    this.router.navigate(['clients/purchasedPDFs'])
  }

  saveNewPDF(){
    this.router.navigate(['clients/saveNewPDF'])
  }

  perfil(){
    this.router.navigate(['clients/perfil'])
  }

  reportedPDFs(){
    this.router.navigate(['admins/reportedPDFs'])
  }

  waitingForValidationPDFs(){
    this.router.navigate(['admins/waitingForValidationPDFs'])
  }
}
