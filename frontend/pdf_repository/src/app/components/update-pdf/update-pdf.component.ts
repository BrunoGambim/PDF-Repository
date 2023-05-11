import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { PdfService } from 'src/app/services/pdf/pdf.service';
import { UpdatePDFService } from 'src/app/services/pdf/update-pdf.service';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';
import { ErrorDialogComponent } from '../error-dialog/error-dialog.component';
import { ERROR_MESSAGE } from 'src/app/config/error.config';

@Component({
  selector: 'app-update-pdf',
  templateUrl: './update-pdf.component.html',
  styleUrls: ['./update-pdf.component.css']
})
export class UpdatePDFComponent {
  fileName: string = '';
  description: string = '';
  pdfID: number = 0;
  file: File | null = null

  constructor(private pdfService: PdfService, updatePDFService: UpdatePDFService,
    private router: Router, userStorageService: UserStorageService, private dialog: MatDialog){
      let pdf = updatePDFService.popPDF()
      if(pdf == null || userStorageService.getLocalUser() == null || userStorageService.getLocalUser()?.email != pdf.ownersEmail){
        router.navigate([''])
      }else{
        this.pdfID = pdf.id
        this.description = pdf.description
      }
  }

  onFileSelected(event: Event){
    const target = event.target as HTMLInputElement;
    if(target != null){
      this.file = (target.files as FileList)[0];
      this.fileName = this.file.name
    }
  }

  updatePDF(){
    if(this.validateFormFields()){
      if(this.file != null){
        this.pdfService.updatePDF(this.pdfID, this.file, this.description).subscribe({next: () => {
          this.router.navigate([''])
        }, error: () => {}})
      }
    }
  }

  private validateFormFields(){
    if(this.file == null){
      this.dialog.open(ErrorDialogComponent,{data: {message: ERROR_MESSAGE.NoFileSelected, navigateToHomeOnClose:false}})
      return false
    } else if(this.description == ''){
      this.dialog.open(ErrorDialogComponent,{data: {message: ERROR_MESSAGE.NoDescriptionProvided, navigateToHomeOnClose:false}})
      return false
    }
    return true
  }
}
