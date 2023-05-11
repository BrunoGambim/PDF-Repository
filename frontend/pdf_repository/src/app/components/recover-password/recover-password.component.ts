import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UpdateUserPasswordService } from 'src/app/services/user/update-user-password.service';
import { UserService } from 'src/app/services/user/user.service';
import { ErrorDialogComponent } from '../error-dialog/error-dialog.component';
import { ERROR_MESSAGE } from 'src/app/config/error.config';

@Component({
  selector: 'app-recover-password',
  templateUrl: './recover-password.component.html',
  styleUrls: ['./recover-password.component.css']
})
export class RecoverPasswordComponent {
  email: string = ''

  constructor(private userService: UserService, private router: Router,
    private updateUSerPasswordService: UpdateUserPasswordService, private dialog: MatDialog){
  }

  sendPassordRecoverMessage(){
    if(this.validateFormFields()){
      this.userService.sendPasswordUpdateCode(this.email).subscribe(res => {
        this.updateUSerPasswordService.putEmail(this.email)
        this.router.navigate(['updatePassword'])
      })
    }
  }

  private validateFormFields(){
    if(this.email == ''){
      this.dialog.open(ErrorDialogComponent,{data: ERROR_MESSAGE.NoEmailProvided})
      return false
    }
    return true
  }
}
