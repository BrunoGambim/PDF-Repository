import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PdfService } from 'src/app/services/pdf/pdf.service';

@Component({
  selector: 'app-save-new-pdf',
  templateUrl: './save-new-pdf.component.html',
  styleUrls: ['./save-new-pdf.component.css']
})
export class SaveNewPDFComponent {
  fileName: string = '';
  description: string = '';
  file: File | null = null

  constructor(private pdfService: PdfService, private router: Router){
  }

  onFileSelected(event: Event){
    const target = event.target as HTMLInputElement;
    if(target != null){
      this.file = (target.files as FileList)[0];
      this.fileName = this.file.name
    }
  }

  savePDF(){
    if(this.file != null){
      this.pdfService.savePDF(this.file, this.description).subscribe(res =>{
        this.router.navigate([''])
      })
    }
  }
}
