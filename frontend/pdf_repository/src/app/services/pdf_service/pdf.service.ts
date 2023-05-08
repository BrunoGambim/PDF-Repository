import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PDFModel } from 'src/app/models/PDFModel';

@Injectable({
  providedIn: 'root'
})
export class PdfService {
  private readonly API = "http://localhost:8080/v1/pdfs"

  constructor(private httpClient: HttpClient) {
  }

  getPDFs(name: string, ownersName:boolean) {
    return this.httpClient.get<PDFModel[]>(this.API+"?name="+name+"&ownersName="+ownersName)
  }
}
