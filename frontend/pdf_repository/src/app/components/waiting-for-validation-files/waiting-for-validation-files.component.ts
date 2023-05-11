import { Component } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
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
  totalElements: number = 0
  pageSize: number = 0
  pageIndex: number = 0

  constructor(private pdfService: PdfService){
    pdfService.getWaitingForValidationPDFs(this.pageIndex).subscribe(res => {
      this.pdfList = res.items
      this.totalElements = res.totalElements
      this.pageIndex = res.pageIndex
      this.pageSize = res.pageSize
    })
  }

  handlePageEvent(e: PageEvent) {
    this.pdfService.getWaitingForValidationPDFs(e.pageIndex).subscribe(res => {
      this.pdfList = res.items
      this.totalElements = res.totalElements
      this.pageIndex = res.pageIndex
      this.pageSize = res.pageSize
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
