import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { PDFModel } from 'src/app/models/pdf';
import { PdfService } from 'src/app/services/pdf/pdf.service';
import { PDFConverter } from 'src/app/utils/PDFConverter';
import { EvaluatePDFComponent } from '../evaluate-pdf/evaluate-pdf.component';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-purchased-pdfs',
  templateUrl: './purchased-pdfs.component.html',
  styleUrls: ['./purchased-pdfs.component.css']
})
export class PurchasedPDFsComponent {

  pdfList: PDFModel[] = []
  name: string = ""
  ownersName: boolean = false

  totalElements: number = 0
  pageSize: number = 0
  pageIndex: number = 0

  constructor(private pdfService: PdfService, private router: Router,
    private dialog: MatDialog){
    pdfService.getPurchasedPDFs(this.pageIndex).subscribe({next: (res) => {
      this.pdfList = res.items
      this.totalElements = res.totalElements
      this.pageIndex = res.pageIndex
      this.pageSize = res.pageSize
    }, error: () => {}})
  }

  handlePageEvent(e: PageEvent) {
    this.pdfService.getPurchasedPDFs(e.pageIndex).subscribe({next: (res) => {
      this.pdfList = res.items
      this.totalElements = res.totalElements
      this.pageIndex = res.pageIndex
      this.pageSize = res.pageSize
    }, error: () => {}})
  }

  reportPDF(id: number) {
    this.pdfService.reportPDFs(id).subscribe({next: () => {
      this.pdfList.forEach(pdf => {
        if(pdf.id == id){
          pdf.status = "REPORTED"
        }
      })
    }, error: () => {}})
  }

  download(pdf: PDFModel){
    PDFConverter.createPDFLink(pdf).click()
  }

  openEvaluateDialog(pdf: PDFModel){
    const dialogRef = this.dialog.open(EvaluatePDFComponent, {data: pdf.id});
    dialogRef.afterClosed().subscribe({next: () => {
      this.pdfService.getPDFById(pdf.id).subscribe({next: (res) => {
        pdf.numberOfEvaluations = res.numberOfEvaluations
        pdf.evaluationMean = res.evaluationMean
        this.pdfList = this.pdfList
      }, error: () => {}})
    }, error: () => {}})
  }
}
