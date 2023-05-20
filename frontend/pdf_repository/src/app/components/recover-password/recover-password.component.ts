import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UpdateUserPasswordService } from 'src/app/services/user/update-user-password.service';
import { UserService } from 'src/app/services/user/user.service';
import { ErrorDialogComponent } from '../../commons/error-dialog/error-dialog.component';
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
      this.userService.sendPasswordUpdateCode(this.email).subscribe({next: () => {
        this.updateUSerPasswordService.putEmail(this.email)
        this.router.navigate(['updatePassword'])
      }, error: () => {}})
    }
  }

  private validateFormFields(){
    if(this.email == ''){
      this.dialog.open(ErrorDialogComponent,{data: {message: ERROR_MESSAGE.NoEmailProvided, navigateToHomeOnClose:false}})
      return false
    }
    return true
  }
}
