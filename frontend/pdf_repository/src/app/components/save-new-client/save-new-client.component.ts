import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ClientModel } from 'src/app/models/client';
import { ClientService } from 'src/app/services/client/client.service';
import { ErrorDialogComponent } from '../error-dialog/error-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { ERROR_MESSAGE } from 'src/app/config/error.config';

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

  constructor(private clientService: ClientService, private router: Router,
    private dialog: MatDialog){
  }

  saveClient(){
    if(this.validateFormFields()){
      this.clientService.saveClient(this.client).subscribe(res => {
        this.router.navigate(['login'])
      })
    }
  }

  private validateFormFields(){
    if(this.client.username == ""){
      this.dialog.open(ErrorDialogComponent,{data: ERROR_MESSAGE.NoUsernameProvided})
      return false
    }else if(this.client.email == ""){
      this.dialog.open(ErrorDialogComponent,{data: ERROR_MESSAGE.NoEmailProvided})
      return false
    }else if(this.client.password == ""){
      this.dialog.open(ErrorDialogComponent,{data: ERROR_MESSAGE.NoPasswordProvided})
      return false
    }
    return true
  }
}
