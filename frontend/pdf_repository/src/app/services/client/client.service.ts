import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_CONFIG } from 'src/app/config/api.config';
import { ClientModel } from 'src/app/models/client';
import { UpdateClientDTO } from 'src/app/models/update_client.dto';

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
}
