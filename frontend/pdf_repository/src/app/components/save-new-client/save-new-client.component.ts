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
      this.clientService.saveClient(this.client).subscribe({next: () => {
        this.router.navigate(['login'])
      }, error: () => {}})
    }
  }

  private validateFormFields(){
    if(this.client.username == ""){
      this.dialog.open(ErrorDialogComponent,{data: {message: ERROR_MESSAGE.NoUsernameProvided, navigateToHomeOnClose:false}})
      return false
    }else if(this.client.email == ""){
      this.dialog.open(ErrorDialogComponent,{data: {message: ERROR_MESSAGE.NoEmailProvided, navigateToHomeOnClose:false}})
      return false
    }else if(this.client.password == ""){
      this.dialog.open(ErrorDialogComponent,{data: {message: ERROR_MESSAGE.NoPasswordProvided, navigateToHomeOnClose:false}})
      return false
    }
    return true
  }
}
