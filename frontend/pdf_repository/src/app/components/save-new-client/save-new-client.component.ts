import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ClientModel } from 'src/app/models/client';
import { ClientService } from 'src/app/services/client/client.service';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';

@Component({
  selector: 'app-save-new-client',
  templateUrl: './save-new-client.component.html',
  styleUrls: ['./save-new-client.component.css']
})
export class SaveNewClientComponent {

  saveClientForm = new FormGroup({
    password: new FormControl('', [ Validators.required, Validators.pattern('^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}$')]),
    confirmPassword: new FormControl('', [ Validators.required ]),
    email: new FormControl('', [ Validators.required, Validators.email ]),
    username: new FormControl('', [ Validators.required ]),
  }, [confirmPasswordValidator]);

  constructor(private clientService: ClientService, private router: Router){
  }

  saveClient(){
    let client: ClientModel = {
      id: 0,
      username: this.saveClientForm.get('username')?.value as string,
      email: this.saveClientForm.get('email')?.value as string,
      password: this.saveClientForm.get('password')?.value as string,
      balance: 0,
    }

    this.clientService.saveClient(client).subscribe({next: () => {
      this.router.navigate(['login'])
    }, error: () => {}})
  }
}

export const confirmPasswordValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const password = control.get('password');
  const confirmPassword = control.get('confirmPassword');
  let valid = password && confirmPassword && password.value === confirmPassword.value
  if(confirmPassword && !valid && !confirmPassword.hasError('mismatch')){
    if(confirmPassword.errors == null){
      confirmPassword.setErrors({mismatch: true})
    }else{
      confirmPassword.errors['mismatch'] = true
    }
  }
  if(confirmPassword && valid && confirmPassword.hasError('mismatch')){
      confirmPassword.setErrors(null)
  }

  return valid ? null : { mismatch: true };
};
