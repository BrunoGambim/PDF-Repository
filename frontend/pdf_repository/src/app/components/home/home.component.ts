import { Component } from '@angular/core';
import { PdfService } from '../../services/pdf/pdf.service';
import { PDFModel } from '../../models/pdf';
import { PDFConverter } from 'src/app/utils/PDFConverter';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';
import { UpdatePDFService } from 'src/app/services/pdf/update-pdf.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { EvaluatePDFComponent } from '../evaluate-pdf/evaluate-pdf.component';
import { AuthenticationState } from 'src/app/models/authentication_state';
import { AuthService } from 'src/app/services/auth/auth.service';
import { PageEvent } from '@angular/material/paginator';
import { API_CONFIG } from 'src/app/config/api.config';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  pdfList: PDFModel[] = []
  name: string = ""
  ownersName: boolean = false
  userEmail: string = ""
  state: AuthenticationState

  totalElements: number = 0
  pageSize: number = 0
  pageIndex: number = 0

  constructor(private pdfService: PdfService, userStorageService: UserStorageService, authService: AuthService,
    private updatePDFService: UpdatePDFService, private router: Router, private dialog: MatDialog){
    this.state = authService.getAuthState()
    pdfService.getPDFs("", false, this.pageIndex).subscribe({next: (res) => {
      this.pdfList = res.items
      this.totalElements = res.totalElements
      this.pageIndex = res.pageIndex
      this.pageSize = res.pageSize
    }, error: () => {}})

    let localuser = userStorageService.getLocalUser()
    if(localuser != null){
      this.userEmail = localuser.email
    }
  }

  handlePageEvent(e: PageEvent) {
    this.pdfService.getPDFs("", false, e.pageIndex).subscribe({next: (res) => {
      this.pdfList = res.items
      this.totalElements = res.totalElements
      this.pageIndex = res.pageIndex
      this.pageSize = res.pageSize
    }, error: () => {}})
  }

  getPDFs(){
    this.pdfService.getPDFs(this.name, this.ownersName, this.pageIndex).subscribe({next: (res) => {
      this.pdfList = res.items
      this.totalElements = res.totalElements
      this.pageIndex = res.pageIndex
      this.pageSize = res.pageSize
    }, error: () => {}})
  }

  download(pdf: PDFModel){
    PDFConverter.createPDFLink(pdf).click()
  }

  purchase(pdf: PDFModel){
    this.pdfService.purchasePDF(pdf.id)
    .subscribe({next: () => {
      this.pdfService.getPDFById(pdf.id).subscribe({next: (res) => {
        pdf.data = res.data
      }, error: () => {}})
    }, error: () => {}})
  }

  deletePDF(id: number){
    this.pdfService.deletePDF(id).subscribe({next: () => {
      this.pdfList = this.pdfList.filter(pdf => pdf.id != id)
      this.totalElements = this.totalElements - 1
    }, error: () => {}})
  }

  updatePDF(pdf: PDFModel){
    this.updatePDFService.putPDF(pdf)
    this.router.navigate(['updatePDF'])
  }

  openEvaluateDialog(pdf: PDFModel){
    const dialogRef = this.dialog.open(EvaluatePDFComponent, {data: pdf.id});
    dialogRef.afterClosed().subscribe({next: () => {
      this.pdfService.getPDFById(pdf.id).subscribe({next: (res) => {
        pdf.numberOfEvaluations = res.numberOfEvaluations
        pdf.evaluationMean = res.evaluationMean
        this.pdfList = this.pdfList
      }, error: () => {}})
    }, error: () => {}})
  }

  reportPDF(id: number) {
    this.pdfService.reportPDFs(id).subscribe({next: () => {
      this.pdfList.forEach(pdf => {
        if(pdf.id == id){
          pdf.status = "REPORTED"
        }
      })
    }, error: () => {}})
  }
}
