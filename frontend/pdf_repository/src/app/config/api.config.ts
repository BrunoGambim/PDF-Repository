export const API_CONFIG = {
  loginURL: "http://localhost:8080/login",
  baseURL: ((<any>window).env)["apiUrl"] + "/v1" || "http://localhost:8080/v1",
  pdfPath: "pdfs",
  userPath: "users",
  clientPath: "clients",
  userRolePath: "users/role",
  ownedPDFsPath: "pdfs/owned",
  purchasedPDFsPath: "pdfs/hasAccess",
  hasAccessPath: "hasAccess",
  reportedPDFsPath: "reported",
  pdfEvaluationPath: "evaluation",
  passwordUpdateCodePath: "updatePasswordCode",
  updatePasswordPath: "password",
  validatePDFPath: "validate",
  waitingForValidation:"waitingForValidation",
  pageSize: 10
}

