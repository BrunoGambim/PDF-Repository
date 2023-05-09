import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UpdateClientPasswordService {
  email: string | null = null

  constructor() { }

  putEmail(email: string) {
    this.email = email;
  }

  popEmail(): string | null {
    let email: string | null = this.email
    this.email = null
    return email
  }
}
