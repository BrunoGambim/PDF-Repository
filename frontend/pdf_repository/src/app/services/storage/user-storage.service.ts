import { Injectable } from '@angular/core';
import { STORAGE_KEYS } from 'src/app/config/storage_keys.config';
import { LocalUser } from 'src/app/models/local_user';

@Injectable({
  providedIn: 'root'
})
export class UserStorageService {

  constructor() { }

  getLocalUser(): LocalUser | null {
    let user = localStorage.getItem(STORAGE_KEYS.localUser)
    if(user == null){
      return null
    } else {
      return JSON.parse(user)
    }
  }

  setLocalUser(user: LocalUser | null) {
    localStorage.setItem(STORAGE_KEYS.localUser, JSON.stringify(user))
  }
}
