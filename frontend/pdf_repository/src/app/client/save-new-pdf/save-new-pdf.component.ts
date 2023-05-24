import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PdfService } from 'src/app/services/pdf/pdf.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-save-new-pdf',
  templateUrl: './save-new-pdf.component.html',
  styleUrls: ['./save-new-pdf.component.css']
})
export class SaveNewPDFComponent {
  fileName: string = '';
  file: File | null = null

  savePDFForm = new FormGroup({
    description: new FormControl('', [ ]),
    pdfInput: new FormControl('', [  ]),
  });

  constructor(private pdfService: PdfService, private router: Router){
  }

  onFileSelected(event: Event){
    const target = event.target as HTMLInputElement;
    if(target != null){
      this.file = (target.files as FileList)[0];
      this.fileName = this.file.name
    }
    this.savePDFForm.get("pdfInput")?.setValue(this.file ? this.fileName: '')
  }

  savePDF(){
    if(this.file != null){
      this.pdfService.savePDF(this.file, this.savePDFForm.get("description")?.value as string).subscribe({next: () => {
        this.router.navigate([''])
      }, error: () => {}})
    }
  }
}
