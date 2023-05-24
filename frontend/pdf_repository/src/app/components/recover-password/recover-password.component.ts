import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UpdateUserPasswordService } from 'src/app/services/user/update-user-password.service';
import { UserService } from 'src/app/services/user/user.service';
import { ErrorDialogComponent } from '../error-dialog/error-dialog.component';
import { ERROR_MESSAGE } from 'src/app/config/error.config';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-recover-password',
  templateUrl: './recover-password.component.html',
  styleUrls: ['./recover-password.component.css']
})
export class RecoverPasswordComponent {
  emailForm = new FormGroup({
    email: new FormControl('', [ Validators.required, Validators.email ]),
  });

  constructor(private userService: UserService, private router: Router,
    private updateUSerPasswordService: UpdateUserPasswordService){
  }

  sendPassordRecoverMessage(){
    this.userService.sendPasswordUpdateCode(this.emailForm.get('email')?.value as string).subscribe({next: () => {
      this.updateUSerPasswordService.putEmail(this.emailForm.get('email')?.value as string)
      this.router.navigate(['updatePassword'])
    }, error: () => {}})
  }
}
