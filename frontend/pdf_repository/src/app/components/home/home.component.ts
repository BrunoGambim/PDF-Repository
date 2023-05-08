import { Component } from '@angular/core';
import { PdfService } from '../../services/pdf/pdf.service';
import { PDFModel } from '../../models/pdf';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  pdfList: PDFModel[] = []
  name: string = ""
  ownersName: boolean = false

  constructor(private pdfService: PdfService){
    pdfService.getPDFs("",false).subscribe(pdfs => {
      this.pdfList = pdfs
    })
  }

  getPDFs(){
    this.pdfService.getPDFs(this.name, this.ownersName).subscribe(pdfs => {
      this.pdfList = pdfs
    })
  }
}
