import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PDFModel } from 'src/app/models/pdf';
import { API_CONFIG } from 'src/app/config/api.config';
import { EvaluationDTO } from 'src/app/models/evaluation.dto';

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
    return this.httpClient.put(`${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}/${id}/${API_CONFIG.reportedPDFsPath}`,{})
  }

  deletePDF(id: number) {
    return this.httpClient.delete(`${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}/${id}`)
  }

  purchasePDF(id: number) {
    return this.httpClient.put(`${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}/${id}/${API_CONFIG.hasAccessPath}`,{})
  }

  savePDF(file: File, description: string) {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("description", description);
    return this.httpClient.post(`${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}`, formData)
  }

  updatePDF(id: number, file: File, description: string) {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("description", description);
    return this.httpClient.put(`${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}/${id}`, formData)
  }

  evaluatePDF(id: number, evaluationValue: number) {
    let dto: EvaluationDTO = {
      evaluationValue: evaluationValue
    }
    return this.httpClient.put(`${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}/${id}/${API_CONFIG.pdfEvaluationPath}`, dto)
  }

  getReportedPDFs(){
    return this.httpClient.get<PDFModel[]>(`${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}/${API_CONFIG.reportedPDFsPath}`)
  }

  getWaitingForValidationPDFs(){
    return this.httpClient.get<PDFModel[]>(`${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}/${API_CONFIG.waitingForValidation}`)
  }

  validatePDF(id: number) {
    return this.httpClient.put(`${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}/${id}/${API_CONFIG.validatePDFPath}`,{})
  }
}
