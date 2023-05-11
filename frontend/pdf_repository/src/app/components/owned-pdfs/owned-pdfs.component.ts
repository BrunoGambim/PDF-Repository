import { Component } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { PDFModel } from 'src/app/models/pdf';
import { PdfService } from 'src/app/services/pdf/pdf.service';
import { UpdatePDFService } from 'src/app/services/pdf/update-pdf.service';
import { PDFConverter } from 'src/app/utils/PDFConverter';

@Component({
  selector: 'app-owned-pdfs',
  templateUrl: './owned-pdfs.component.html',
  styleUrls: ['./owned-pdfs.component.css']
})
export class OwnedPDFsComponent {
  pdfList: PDFModel[] = []
  name: string = ""
  ownersName: boolean = false

  totalElements: number = 0
  pageSize: number = 0
  pageIndex: number = 0

  constructor(private pdfService: PdfService, private updatePDFService: UpdatePDFService,
    private router: Router){
    pdfService.getOwnedPDFs(this.pageIndex).subscribe({next: (res) => {
      this.pdfList = res.items
      this.totalElements = res.totalElements
      this.pageIndex = res.pageIndex
      this.pageSize = res.pageSize
    }, error: () => {}})
  }

  handlePageEvent(e: PageEvent) {
    this.pdfService.getOwnedPDFs(e.pageIndex).subscribe({next: (res) => {
      this.pdfList = res.items
      this.totalElements = res.totalElements
      this.pageIndex = res.pageIndex
      this.pageSize = res.pageSize
    }, error: () => {}})
  }

  deletePDF(id: number){
    this.pdfService.deletePDF(id).subscribe({next: () => {
      this.totalElements = this.totalElements - 1
      this.pdfList = this.pdfList.filter(pdf => pdf.id != id)
    }, error: () => {}})
  }

  download(pdf: PDFModel){
    PDFConverter.createPDFLink(pdf).click()
  }

  updatePDF(pdf: PDFModel){
    this.updatePDFService.putPDF(pdf)
    this.router.navigate(['updatePDF'])
  }
}
