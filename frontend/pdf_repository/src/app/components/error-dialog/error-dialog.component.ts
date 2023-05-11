import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ErrorDialogData } from 'src/app/models/error_dialog_data';

@Component({
  selector: 'app-error-dialog',
  templateUrl: './error-dialog.component.html',
  styleUrls: ['./error-dialog.component.css']
})
export class ErrorDialogComponent {
  constructor(public dialogRef: MatDialogRef<ErrorDialogComponent>, private router: Router,
    @Inject(MAT_DIALOG_DATA) public data: ErrorDialogData) {
  }

  close(){
    if(this.data.navigateToHomeOnClose){
      this.router.navigate([''])
    }
    this.dialogRef.close();
  }
}
