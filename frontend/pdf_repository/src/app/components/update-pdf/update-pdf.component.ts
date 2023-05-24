import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
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
  pdfID: number = 0;
  file: File | null = null

  updatePDFForm = new FormGroup({
    description: new FormControl('', [ Validators.required ]),
    pdfInput: new FormControl('', [ Validators.required ]),
  });

  constructor(private pdfService: PdfService, updatePDFService: UpdatePDFService,
    private router: Router, userStorageService: UserStorageService){
      let pdf = updatePDFService.popPDF()
      if(pdf == null || userStorageService.getLocalUser() == null || userStorageService.getLocalUser()?.email != pdf.ownersEmail){
        router.navigate([''])
      }else{
        this.pdfID = pdf.id
        this.updatePDFForm.get("description")?.setValue(pdf.description)
      }
  }

  onFileSelected(event: Event){
    const target = event.target as HTMLInputElement;
    if(target != null){
      this.file = (target.files as FileList)[0];
      this.fileName = this.file.name
    }
    this.updatePDFForm.get("pdfInput")?.setValue(this.file ? this.fileName: '')
  }

  updatePDF(){
    if(this.file != null){
      this.pdfService.updatePDF(this.pdfID, this.file, this.updatePDFForm.get("description")?.value as string).subscribe({next: () => {
        this.router.navigate([''])
      }, error: () => {}})
    }
  }
}
