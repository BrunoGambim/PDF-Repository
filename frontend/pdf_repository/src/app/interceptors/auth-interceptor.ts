import { HTTP_INTERCEPTORS, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { UserStorageService } from "../services/storage/user-storage.service";
import { AUTHORIZATION_HEADER, TOKEN_PREFIX } from "../config/auth.config";
import { API_CONFIG } from "../config/api.config";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private userStorageService: UserStorageService){
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let local_user = this.userStorageService.getLocalUser()
    let requestToAPI = req.url.substring(0, API_CONFIG.baseURL.length) == API_CONFIG.baseURL
    if(local_user != null && requestToAPI){
      const authReq = req.clone({headers: req.headers.set(AUTHORIZATION_HEADER, TOKEN_PREFIX + local_user.token)})
      return next.handle(authReq)
    }
    return next.handle(req)
  }
}

export const AuthInterceptorProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: AuthInterceptor,
  multi: true
}
