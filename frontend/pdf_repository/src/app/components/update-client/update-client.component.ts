import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AUTHORIZATION_HEADER } from 'src/app/config/auth.config';
import { ClientModel } from 'src/app/models/client';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ClientService } from 'src/app/services/client/client.service';

@Component({
  selector: 'app-update-client',
  templateUrl: './update-client.component.html',
  styleUrls: ['./update-client.component.css']
})
export class UpdateClientComponent {
  constructor(private clientService: ClientService, private authService: AuthService,
    public dialogRef: MatDialogRef<UpdateClientComponent>,
    @Inject(MAT_DIALOG_DATA) public client: ClientModel) {
  }

  update(){
    this.clientService.updateClient(this.client).subscribe(res =>{
      this.authService.successfulLogin(res.headers.get(AUTHORIZATION_HEADER))
      this.dialogRef.close()
    })
  }
}
