import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PDFModel } from 'src/app/models/pdf';
import { API_CONFIG } from 'src/app/config/api.config';

@Injectable({
  providedIn: 'root'
})
export class PdfService {
  constructor(private httpClient: HttpClient) {
  }

  getPDFs(name: string, ownersName:boolean) {
    return this.httpClient.get<PDFModel[]>(`${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}?name=${name}&ownersName=${ownersName}`)
  }

  getOwnedPDFs() {
    return this.httpClient.get<PDFModel[]>(`${API_CONFIG.baseURL}/${API_CONFIG.ownedPDFsPath}`)
  }

  getPurchasedPDFs() {
    return this.httpClient.get<PDFModel[]>(`${API_CONFIG.baseURL}/${API_CONFIG.purchasedPDFsPath}`)
  }

  reportPDFs(id: number) {
    return this.httpClient.put(`${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}/${id}/${API_CONFIG.reportPDFPath}`,{})
  }

  deletePDF(id: number) {
    return this.httpClient.delete(`${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}/${id}`)
  }
}
