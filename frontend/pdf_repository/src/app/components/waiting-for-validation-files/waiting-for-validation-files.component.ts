import { Component } from '@angular/core';
import { PDFModel } from 'src/app/models/pdf';
import { PdfService } from 'src/app/services/pdf/pdf.service';
import { PDFConverter } from 'src/app/utils/PDFConverter';

@Component({
  selector: 'app-waiting-for-validation-files',
  templateUrl: './waiting-for-validation-files.component.html',
  styleUrls: ['./waiting-for-validation-files.component.css']
})
export class WaitingForValidationFilesComponent {
  pdfList: PDFModel[] = []

  constructor(private pdfService: PdfService){
    pdfService.getWaitingForValidationPDFs().subscribe(pdfs => {
      this.pdfList = pdfs
    })
  }

  download(pdf: PDFModel){
    PDFConverter.createPDFLink(pdf).click()
  }

  deletePDF(id: number){
    this.pdfService.deletePDF(id).subscribe(res => {
      this.pdfList = this.pdfList.filter(pdf => pdf.id != id)
    })
  }

  validatePDF(id: number){
    this.pdfService.validatePDF(id).subscribe(res => {
      this.pdfList = this.pdfList.filter(pdf => pdf.id != id)
    });
  }
}
