import { Component } from '@angular/core';
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
  name: string = ""
  ownersName: boolean = false

  constructor(private pdfService: PdfService){
    pdfService.getReportedPDFs().subscribe(pdfs => {
      this.pdfList = pdfs
    })
  }

  download(pdf: PDFModel){
    PDFConverter.createPDFLink(pdf).click()
  }
}
