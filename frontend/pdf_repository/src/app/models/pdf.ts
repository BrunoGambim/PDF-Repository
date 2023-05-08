export interface PDFModel {
  id: number;
  name: string;
  description: string;
  ownersName: string;
  price: number;
  evaluationMean: number;
  numberOfEvaluations: number;
  size: number;
  data: number[]
}
