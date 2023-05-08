import { Component } from '@angular/core';
import { PDFModel } from 'src/app/models/pdf';
import { PdfService } from 'src/app/services/pdf/pdf.service';

@Component({
  selector: 'app-purchased-pdfs',
  templateUrl: './purchased-pdfs.component.html',
  styleUrls: ['./purchased-pdfs.component.css']
})
export class PurchasedPDFsComponent {

  pdfList: PDFModel[] = []
  name: string = ""
  ownersName: boolean = false

  constructor(private pdfService: PdfService){
    pdfService.getPurchasedPDFs().subscribe(pdfs => {
      this.pdfList = pdfs
    })
  }

  reportPDF(id: number) {
    this.pdfService.reportPDFs(id)
  }
}
