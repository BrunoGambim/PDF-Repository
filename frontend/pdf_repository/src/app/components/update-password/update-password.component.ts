import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UpdateUserPasswordService } from 'src/app/services/user/update-user-password.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: ['./update-password.component.css']
})
export class UpdatePasswordComponent {
  email: string = ''
  password: string = ''
  code: string = ''

  constructor(private updateUserPasswordService: UpdateUserPasswordService, private router: Router,
    private userService: UserService){
      let email = updateUserPasswordService.popEmail()
      if(email == null ){
        router.navigate([''])
      }else{
        this.email = email;
      }
  }

  updatePassword(){
    this.userService.updatePassword(this.email, this.code, this.password).subscribe(res =>{
      this.router.navigate(['login'])
    })
  }
}
