import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_CONFIG } from 'src/app/config/api.config';
import { EmailDTO } from 'src/app/models/email.dto';
import { UpdatePasswordDTO } from 'src/app/models/update_password.dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  sendPasswordUpdateCode(email: string) {
    let dto: EmailDTO = {
      email: email,
    }
    return this.httpClient.post(`${API_CONFIG.baseURL}/${API_CONFIG.userPath}/${API_CONFIG.passwordUpdateCodePath}`, dto)
  }

  updatePassword(email: string, code: string, password: string) {
    let dto: UpdatePasswordDTO = {
      email: email,
      code: code,
      password: password,
    }
    return this.httpClient.put(`${API_CONFIG.baseURL}/${API_CONFIG.userPath}/${API_CONFIG.updatePasswordPath}`, dto)
  }
}
