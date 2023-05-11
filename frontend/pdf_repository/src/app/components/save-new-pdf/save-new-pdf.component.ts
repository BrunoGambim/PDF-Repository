import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { PdfService } from 'src/app/services/pdf/pdf.service';
import { ErrorDialogComponent } from '../error-dialog/error-dialog.component';
import { ERROR_MESSAGE } from 'src/app/config/error.config';

@Component({
  selector: 'app-save-new-pdf',
  templateUrl: './save-new-pdf.component.html',
  styleUrls: ['./save-new-pdf.component.css']
})
export class SaveNewPDFComponent {
  fileName: string = '';
  description: string = '';
  file: File | null = null

  constructor(private pdfService: PdfService, private router: Router,
    private dialog: MatDialog){
  }

  onFileSelected(event: Event){
    const target = event.target as HTMLInputElement;
    if(target != null){
      this.file = (target.files as FileList)[0];
      this.fileName = this.file.name
    }
  }

  savePDF(){
    if(this.validateFormFields() && this.file != null){
      this.pdfService.savePDF(this.file, this.description).subscribe(res =>{
        this.router.navigate([''])
      })
    }
  }

  private validateFormFields(){
    if(this.file == null){
      this.dialog.open(ErrorDialogComponent,{data: ERROR_MESSAGE.NoFileSelected})
      return false
    } else if(this.description == ''){
      this.dialog.open(ErrorDialogComponent,{data: ERROR_MESSAGE.NoDescriptionProvided})
      return false
    }
    return true
  }
}