import { Injectable } from '@angular/core';
import jwtDecode from 'jwt-decode';
import { STORAGE_KEYS } from 'src/app/config/storage_keys.config';
import { LocalUser } from 'src/app/models/local_user';

@Injectable({
  providedIn: 'root'
})
export class UserStorageService {

  constructor() { }

  getLocalUser(): LocalUser | null {
    let token = localStorage.getItem(STORAGE_KEYS.token)
    if(token == null){
      return null
    }
    let tokenSub = jwtDecode<any>(token).sub
    return JSON.parse(tokenSub)
  }

  setToken(token: string | null) {
    if(token != null){
      localStorage.setItem(STORAGE_KEYS.token, token)
    }else {
      localStorage.removeItem(STORAGE_KEYS.token)
    }
  }

  getToken(): String | null {
    return localStorage.getItem(STORAGE_KEYS.token)
  }
}
