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

  constructor(private pdfService: PdfService, userStorageService: UserStorageService, authService: AuthService,
    private updatePDFService: UpdatePDFService, private router: Router, private dialog: MatDialog){
    this.state = AuthenticationState.UNAUTHENTICATED
    authService.getRole().subscribe(state => {
      this.state = state
    })

    pdfService.getPDFs("",false).subscribe(pdfs => {
      this.pdfList = pdfs
    })

    let localuser = userStorageService.getLocalUser()
    if(localuser != null){
      this.userEmail = localuser.email
    }
  }

  getPDFs(){
    this.pdfService.getPDFs(this.name, this.ownersName).subscribe(pdfs => {
      this.pdfList = pdfs
    })
  }

  download(pdf: PDFModel){
    PDFConverter.createPDFLink(pdf).click()
  }

  purchase(pdf: PDFModel){
    this.pdfService.purchasePDF(pdf.id).subscribe(res => {
      //TODO reload page
    })
  }

  deletePDF(id: number){
    this.pdfService.deletePDF(id).subscribe(res => {
      this.pdfList = this.pdfList.filter(pdf => pdf.id != id)
    })
  }

  updatePDF(pdf: PDFModel){
    this.updatePDFService.putPDF(pdf)
    this.router.navigate(['updatePDF'])
  }

  openEvaluateDialog(pdf: PDFModel){
    const dialogRef = this.dialog.open(EvaluatePDFComponent, {data: pdf.id});
  }

  reportPDF(id: number) {
    this.pdfService.reportPDFs(id).subscribe(res => {
      this.pdfList.forEach(pdf => {
        if(pdf.id == id){
          pdf.status = "REPORTED"
        }
      })
    })
  }

  validatePDF(id: number){
    this.pdfService.validatePDF(id).subscribe(res => {
      this.pdfList = this.pdfList.filter(pdf => pdf.id != id)
    });
  }
}
