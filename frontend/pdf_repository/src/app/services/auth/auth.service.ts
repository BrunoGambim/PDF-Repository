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

  state: Subject<AuthenticationState>

  constructor(private httpClient: HttpClient, private userStorageService: UserStorageService) {
    this.state = new Subject<AuthenticationState>()
  }

  authenticate(email: string, password: string){
    let dto: LoginDTO = {
      email: email,
      password: password
    }
    return this.httpClient.post(API_CONFIG.loginURL, dto,{ observe: 'response', responseType: 'text' })
  }

  successfulLogin(authHeader: string | null){
    if(authHeader != null){
      let token = authHeader.substring(TOKEN_PREFIX_SIZE)
      let user: LocalUser = {
        token: token,
        email: jwtDecode<any>(token).sub
      }
      this.userStorageService.setLocalUser(user)
      this.updateRoles()
    }
  }

  getRole(){
    let user = this.userStorageService.getLocalUser()
    if(user == null){
      return new Observable<AuthenticationState>((subscriber) => {
        subscriber.next(AuthenticationState.UNAUTHENTICATED)
      })
    } else {
      return this.httpClient.get<UserRoleDTO>(`${API_CONFIG.baseURL}/${API_CONFIG.userRolePath}?email=${user.email}`)
        .pipe<AuthenticationState>(map(dto => {
          if(dto.role == API_ROLES.admin){
            return AuthenticationState.ADMIN
          }else{
            return AuthenticationState.CLIENT
          }
        }))
    }
  }

  getRoleUpdates(){
    return this.state
  }

  private updateRoles(){
    this.getRole().subscribe(res => {
      this.state.next(res)
    })
  }

  logout(){
    this.userStorageService.setLocalUser(null)
    this.updateRoles()
  }
}
