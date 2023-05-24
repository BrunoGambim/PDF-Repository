import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UpdateUserPasswordService } from 'src/app/services/user/update-user-password.service';
import { UserService } from 'src/app/services/user/user.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: ['./update-password.component.css']
})
export class UpdatePasswordComponent {
  email: string = ''
  updatePasswordForm = new FormGroup({
    password: new FormControl('', [ Validators.required, Validators.pattern('^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}$')]),
    code: new FormControl('', [ Validators.required ]),
  });

  constructor(updateUserPasswordService: UpdateUserPasswordService, private router: Router,
    private userService: UserService){
      let email = updateUserPasswordService.popEmail()
      if(email == null ){
        router.navigate([''])
      }else{
        this.email = email;
      }
  }

  updatePassword(){
    this.userService.updatePassword(this.email, this.updatePasswordForm.get('code')?.value as string,
      this.updatePasswordForm.get('password')?.value as string).subscribe({next: () => {
      this.router.navigate(['login'])
    }, error: () => {}})
  }

}
