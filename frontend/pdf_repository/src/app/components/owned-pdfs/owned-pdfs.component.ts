import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PDFModel } from 'src/app/models/pdf';
import { PdfService } from 'src/app/services/pdf/pdf.service';
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

  constructor(private pdfService: PdfService, private router: Router){
    pdfService.getOwnedPDFs().subscribe(pdfs => {
      this.pdfList = pdfs
    })
  }

  deletePDF(id: number){
    this.pdfService.deletePDF(id).subscribe(res => {
      this.pdfList = this.pdfList.filter(pdf => pdf.id != id)
    })
  }

  download(pdf: PDFModel){
    PDFConverter.createPDFLink(pdf).click()
  }
}
