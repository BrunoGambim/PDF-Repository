import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ClientModel } from 'src/app/models/client';
import { ClientService } from 'src/app/services/client/client.service';

@Component({
  selector: 'app-update-client',
  templateUrl: './update-client.component.html',
  styleUrls: ['./update-client.component.css']
})
export class UpdateClientComponent {
  constructor(private clientService: ClientService,
    public dialogRef: MatDialogRef<UpdateClientComponent>,
    @Inject(MAT_DIALOG_DATA) public client: ClientModel) {
  }

  update(){
    this.clientService.updateClient(this.client).subscribe(res =>{
      this.dialogRef.close()
    })
  }
}
