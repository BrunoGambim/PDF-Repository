import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PdfService } from 'src/app/services/pdf/pdf.service';
import { UpdatePDFService } from 'src/app/services/pdf/update-pdf.service';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';

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
    private router: Router, userStorageService: UserStorageService){
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
    if(this.file != null){
      this.pdfService.updatePDF(this.pdfID, this.file, this.description).subscribe(res =>{
        this.router.navigate([''])
      })
    }
  }
}
