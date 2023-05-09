import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_CONFIG } from 'src/app/config/api.config';
import { ClientModel } from 'src/app/models/client';
import { UpdateClientDTO } from 'src/app/models/update_client.dto';
import { SaveClientDTO } from 'src/app/models/save_client.dto';
import { EmailDTO } from 'src/app/models/email.dto';
import { UpdatePasswordDTO } from 'src/app/models/update_password.dto';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  constructor(private httpClient: HttpClient){

  }

  findClient(email: string){
    return this.httpClient.get<ClientModel>(`${API_CONFIG.baseURL}/${API_CONFIG.clientPath}?email=${email}`)
  }

  updateClient(client: ClientModel){
    let dto: UpdateClientDTO = {
      email: client.email,
      username: client.username
    }
    return this.httpClient.put<ClientModel>(`${API_CONFIG.baseURL}/${API_CONFIG.clientPath}/${client.id}`, dto)
  }

  saveClient(client: ClientModel) {
    let dto: SaveClientDTO = {
      email: client.email,
      password: client.password,
      username: client.username,
    }
    return this.httpClient.post(`${API_CONFIG.baseURL}/${API_CONFIG.clientPath}`, dto)
  }

  sendPasswordUpdateCode(email: string) {
    let dto: EmailDTO = {
      email: email,
    }
    return this.httpClient.post(`${API_CONFIG.baseURL}/${API_CONFIG.clientPath}/${API_CONFIG.passwordUpdateCodePath}`, dto)
  }

  updatePassword(email: string, code: string, password: string) {
    let dto: UpdatePasswordDTO = {
      email: email,
      code: code,
      password: password,
    }
    return this.httpClient.put(`${API_CONFIG.baseURL}/${API_CONFIG.clientPath}/${API_CONFIG.updatePasswordPath}`, dto)
  }
}
