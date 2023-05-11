import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AUTHORIZATION_HEADER } from 'src/app/config/auth.config';
import { ClientModel } from 'src/app/models/client';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ClientService } from 'src/app/services/client/client.service';
import { ErrorDialogComponent } from '../error-dialog/error-dialog.component';
import { ERROR_MESSAGE } from 'src/app/config/error.config';

@Component({
  selector: 'app-update-client',
  templateUrl: './update-client.component.html',
  styleUrls: ['./update-client.component.css']
})
export class UpdateClientComponent {
  constructor(private clientService: ClientService, private authService: AuthService,
    public dialogRef: MatDialogRef<UpdateClientComponent>, private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public client: ClientModel) {
  }

  update(){
    if(this.validateFormFields()){
      this.clientService.updateClient(this.client).subscribe(res =>{
        this.authService.successfulLogin(res.headers.get(AUTHORIZATION_HEADER))
        this.dialogRef.close()
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
    }
    return true
  }
}
