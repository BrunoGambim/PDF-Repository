import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PDFModel } from 'src/app/models/pdf';
import { API_CONFIG } from 'src/app/config/api.config';
import { EvaluationDTO } from 'src/app/models/evaluation.dto';
import { PageDTO } from 'src/app/models/page.dto';

@Injectable({
  providedIn: 'root'
})
export class PdfService {
  constructor(private httpClient: HttpClient) {
  }

  getPDFs(name: string, ownersName:boolean, pageIndex: number) {
    console.log(`${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}?name=${name}&ownersName=${ownersName}&pageSize=${API_CONFIG.pageSize}&pageIndex=${pageIndex}`)
    return this.httpClient.get<PageDTO<PDFModel>>(
      `${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}?name=${name}&ownersName=${ownersName}&pageSize=${API_CONFIG.pageSize}&pageIndex=${pageIndex}`)
  }

  getPDFById(id: number) {
    return this.httpClient.get<PDFModel>(`${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}/${id}`)
  }

  getOwnedPDFs(pageIndex: number) {
    return this.httpClient.get<PageDTO<PDFModel>>(
      `${API_CONFIG.baseURL}/${API_CONFIG.ownedPDFsPath}?pageSize=${API_CONFIG.pageSize}&pageIndex=${pageIndex}`)
  }

  getPurchasedPDFs(pageIndex: number) {
    return this.httpClient.get<PageDTO<PDFModel>>(
      `${API_CONFIG.baseURL}/${API_CONFIG.purchasedPDFsPath}?pageSize=${API_CONFIG.pageSize}&pageIndex=${pageIndex}`)
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

  getReportedPDFs(pageIndex: number){
    return this.httpClient.get<PageDTO<PDFModel>>(
      `${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}/${API_CONFIG.reportedPDFsPath}?pageSize=${API_CONFIG.pageSize}&pageIndex=${pageIndex}`)
  }

  getWaitingForValidationPDFs(pageIndex: number){
    return this.httpClient.get<PageDTO<PDFModel>>(
      `${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}/${API_CONFIG.waitingForValidation}?pageSize=${API_CONFIG.pageSize}&pageIndex=${pageIndex}`)
  }

  validatePDF(id: number) {
    return this.httpClient.put(`${API_CONFIG.baseURL}/${API_CONFIG.pdfPath}/${id}/${API_CONFIG.validatePDFPath}`,{})
  }
}
