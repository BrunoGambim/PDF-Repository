import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_ROLES, TOKEN_PREFIX_SIZE } from 'src/app/config/auth.config';
import { LocalUser } from 'src/app/models/local_user';
import { LoginDTO } from 'src/app/models/login.dto';
import { UserStorageService } from '../storage/user-storage.service';
import jwtDecode from 'jwt-decode';
import { API_CONFIG } from 'src/app/config/api.config';
import { Observable, Subject, map } from 'rxjs';
import { UserRoleDTO } from 'src/app/models/user_role.dto';
import { AuthenticationState } from 'src/app/models/authentication_state';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  stateSubject: Subject<AuthenticationState>

  constructor(private httpClient: HttpClient, private userStorageService: UserStorageService) {
    this.stateSubject = new Subject<AuthenticationState>()
  }

  authenticate(email: string, password: string){
    let dto: LoginDTO = {
      email: email,
      password: password
    }
    return this.httpClient.post(API_CONFIG.loginURL, dto, { observe: 'response', responseType: 'text' })
  }

  successfulLogin(authHeader: string | null){
    if(authHeader != null){
      let token = authHeader.substring(TOKEN_PREFIX_SIZE)
      this.userStorageService.setToken(token)
      this.updateAuthState()
    }
  }

  getAuthState(): AuthenticationState{
    let user = this.userStorageService.getLocalUser()
    if(user == null){
      return AuthenticationState.UNAUTHENTICATED
    }

    if(user.role == 'ADMIN'){
      return AuthenticationState.ADMIN
    }

    return AuthenticationState.CLIENT
  }

  getAuthStateUpdates(){
    return this.stateSubject
  }

  updateAuthState(){
    this.stateSubject.next(this.getAuthState())
  }

  logout(){
    this.userStorageService.setToken(null)
    this.updateAuthState()
  }
}
