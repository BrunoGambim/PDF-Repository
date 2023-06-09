import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { API_CONFIG } from 'src/app/config/api.config';
import { ClientModel } from 'src/app/models/client';
import { ClientService } from 'src/app/services/client/client.service';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';
import { UpdateClientComponent } from '../update-client/update-client.component';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent {

  client: ClientModel = {
    balance: 0,
    email: "",
    id: 0,
    username: "",
    password:""
  }

  constructor(clientService: ClientService, userStorageService: UserStorageService,
    router: Router, private dialog: MatDialog){
    let user = userStorageService.getLocalUser()
    if(user == null){
      router.navigate([''])
    } else {
      clientService.findClient(user.email).subscribe({next: (res) => {
        this.client = res
      }, error: () => { router.navigate(['']) }})
    }
  }

  openUpdateClientDialog(){
    this.dialog.open(UpdateClientComponent, {data: this.client});
  }
}
