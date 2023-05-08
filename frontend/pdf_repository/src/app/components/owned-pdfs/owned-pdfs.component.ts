import { Component } from '@angular/core';
import { PDFModel } from 'src/app/models/pdf';
import { PdfService } from 'src/app/services/pdf/pdf.service';

@Component({
  selector: 'app-owned-pdfs',
  templateUrl: './owned-pdfs.component.html',
  styleUrls: ['./owned-pdfs.component.css']
})
export class OwnedPDFsComponent {
  pdfList: PDFModel[] = []
  name: string = ""
  ownersName: boolean = false

  constructor(private pdfService: PdfService){
    pdfService.getOwnedPDFs().subscribe(pdfs => {
      this.pdfList = pdfs
    })
  }
}
