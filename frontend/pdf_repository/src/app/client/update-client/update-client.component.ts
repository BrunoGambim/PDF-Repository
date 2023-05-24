import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AUTHORIZATION_HEADER } from 'src/app/config/auth.config';
import { ClientModel } from 'src/app/models/client';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ClientService } from 'src/app/services/client/client.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-update-client',
  templateUrl: './update-client.component.html',
  styleUrls: ['./update-client.component.css']
})
export class UpdateClientComponent {
  updateClientForm = new FormGroup({
    email: new FormControl(this.client.email, [ Validators.required, Validators.email ]),
    username: new FormControl(this.client.username, [ Validators.required ]),
  });

  constructor(private clientService: ClientService, private authService: AuthService,
    public dialogRef: MatDialogRef<UpdateClientComponent>,
    @Inject(MAT_DIALOG_DATA) public client: ClientModel) {
  }

  update(){
    this.client.username = this.updateClientForm.get('username')?.value as string
    this.client.email = this.updateClientForm.get('email')?.value as string

    this.clientService.updateClient(this.client).subscribe({next: (res) => {
      this.authService.successfulLogin(res.headers.get(AUTHORIZATION_HEADER))
      this.dialogRef.close()
    }, error: () => {}})
  }
}
