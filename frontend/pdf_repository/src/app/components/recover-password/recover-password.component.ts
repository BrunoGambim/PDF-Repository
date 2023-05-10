import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UpdateUserPasswordService } from 'src/app/services/user/update-user-password.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-recover-password',
  templateUrl: './recover-password.component.html',
  styleUrls: ['./recover-password.component.css']
})
export class RecoverPasswordComponent {
  email: string = ''

  constructor(private userService: UserService, private router: Router,
    private updateUSerPasswordService: UpdateUserPasswordService){
  }

  sendPassordRecoverMessage(){
    this.userService.sendPasswordUpdateCode(this.email).subscribe(res => {
      this.updateUSerPasswordService.putEmail(this.email)
      this.router.navigate(['updatePassword'])
    })
  }
}
