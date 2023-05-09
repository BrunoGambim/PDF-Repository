import { PDFModel } from "../models/pdf";

export class PDFConverter {
  static createPDFLink(pdf: PDFModel){
    let buffer = PDFConverter.base64ToArrayBuffer(pdf.data.toString())
    var blob = new Blob([buffer], {type: "application/pdf"});
    var link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    var fileName = pdf.name;
    link.download = fileName;
    return link
  }

  private static base64ToArrayBuffer(base64: string) {
    var binaryString = window.atob(base64);
    var binaryLen = binaryString.length;
    var bytes = new Uint8Array(binaryLen);
    for (var i = 0; i < binaryLen; i++) {
       var ascii = binaryString.charCodeAt(i);
       bytes[i] = ascii;
    }
    return bytes;
  }
}


