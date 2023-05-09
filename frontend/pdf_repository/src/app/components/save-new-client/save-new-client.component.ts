import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ClientModel } from 'src/app/models/client';
import { ClientService } from 'src/app/services/client/client.service';

@Component({
  selector: 'app-save-new-client',
  templateUrl: './save-new-client.component.html',
  styleUrls: ['./save-new-client.component.css']
})
export class SaveNewClientComponent {

  client: ClientModel = {
    id: 0,
    username: "",
    email: "",
    password: "",
    balance: 0,
  }

  constructor(private clientService: ClientService, private router: Router){
  }

  saveClient(){
    this.clientService.saveClient(this.client).subscribe(res => {
      this.router.navigate(['login'])
    })
  }
}
