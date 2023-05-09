import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { PdfService } from 'src/app/services/pdf/pdf.service';

@Component({
  selector: 'app-evaluate-pdf',
  templateUrl: './evaluate-pdf.component.html',
  styleUrls: ['./evaluate-pdf.component.css']
})
export class EvaluatePDFComponent {
  evaluationValue: number = 0
  hoverValue: number = 0

  constructor(private pdfService: PdfService,
    public dialogRef: MatDialogRef<EvaluatePDFComponent>,
    @Inject(MAT_DIALOG_DATA) private pdfId: number) {
  }

  mouseEnterTwo(){
    this.hoverValue = 2;
  }

  mouseEnterFour(){
    this.hoverValue = 4;
  }

  mouseEnterSix(){
    this.hoverValue = 6;
  }

  mouseEnterEight(){
    this.hoverValue = 8;
  }

  mouseEnterTen(){
    this.hoverValue = 10;
  }

  mouseClickTwo(){
    this.evaluationValue = 2;
  }

  mouseClickFour(){
    this.evaluationValue = 4;
  }

  mouseClickSix(){
    this.evaluationValue = 6;
  }

  mouseClickEight(){
    this.evaluationValue = 8;
  }

  mouseClickTen(){
    this.evaluationValue = 10;
  }

  mouseLeave(){
    this.hoverValue = this.evaluationValue
  }

  evaluatePDF(){
    this.pdfService.evaluatePDF(this.pdfId, this.evaluationValue).subscribe(res => {
      this.dialogRef.close()
    })
  }
}
