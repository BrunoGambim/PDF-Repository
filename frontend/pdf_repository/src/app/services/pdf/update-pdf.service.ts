import { Injectable } from '@angular/core';
import { PDFModel } from 'src/app/models/pdf';

@Injectable({
  providedIn: 'root'
})
export class UpdatePDFService {

  pdf: PDFModel | null = null

  constructor() { }

  putPDF(pdf: PDFModel) {
    this.pdf = pdf;
  }

  popPDF(): PDFModel | null {
    let pdf: PDFModel | null = this.pdf
    this.pdf = null
    return pdf
  }
}
