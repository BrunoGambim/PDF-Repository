import { Component } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { PDFModel } from 'src/app/models/pdf';
import { PdfService } from 'src/app/services/pdf/pdf.service';
import { PDFConverter } from 'src/app/utils/PDFConverter';

@Component({
  selector: 'app-reported-files',
  templateUrl: './reported-files.component.html',
  styleUrls: ['./reported-files.component.css']
})
export class ReportedFilesComponent {
  pdfList: PDFModel[] = []

  totalElements: number = 0
  pageSize: number = 0
  pageIndex: number = 0

  constructor(private pdfService: PdfService){
    pdfService.getReportedPDFs(this.pageIndex).subscribe(res => {
      this.pdfList = res.items
      this.totalElements = res.totalElements
      this.pageIndex = res.pageIndex
      this.pageSize = res.pageSize
    })
  }

  handlePageEvent(e: PageEvent) {
    this.pdfService.getReportedPDFs(e.pageIndex).subscribe(res => {
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
