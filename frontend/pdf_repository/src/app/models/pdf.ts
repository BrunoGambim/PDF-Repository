export interface PDFModel {
  id: number;
  name: string;
  status: string;
  description: string;
  ownersName: string;
  ownersEmail: string;
  price: number;
  evaluationMean: number;
  numberOfEvaluations: number;
  size: number;
  data: number[]
  canBeAccessedBy: string[]
}
