import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UpdateUserPasswordService } from 'src/app/services/user/update-user-password.service';
import { UserService } from 'src/app/services/user/user.service';
import { ErrorDialogComponent } from '../../commons/error-dialog/error-dialog.component';
import { ERROR_MESSAGE } from 'src/app/config/error.config';

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: ['./update-password.component.css']
})
export class UpdatePasswordComponent {
  email: string = ''
  password: string = ''
  code: string = ''

  constructor(updateUserPasswordService: UpdateUserPasswordService, private router: Router,
    private userService: UserService, private dialog: MatDialog){
      let email = updateUserPasswordService.popEmail()
      if(email == null ){
        router.navigate([''])
      }else{
        this.email = email;
      }
  }

  updatePassword(){
    if(this.validateFormFields()){
      this.userService.updatePassword(this.email, this.code, this.password).subscribe({next: () => {
        this.router.navigate(['login'])
      }, error: () => {}})
    }
  }

  private validateFormFields(){
    if(this.password == ''){
      this.dialog.open(ErrorDialogComponent,{data: {message: ERROR_MESSAGE.NoPasswordProvided, navigateToHomeOnClose:false}})
      return false
    } else if(this.code == ''){
      this.dialog.open(ErrorDialogComponent,{data: {message: ERROR_MESSAGE.NoCodeProvided, navigateToHomeOnClose:false}})
      return false
    }
    return true
  }
}
