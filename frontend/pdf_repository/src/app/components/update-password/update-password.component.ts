import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ClientService } from 'src/app/services/client/client.service';
import { UpdateClientPasswordService } from 'src/app/services/client/update-client-password.service';

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: ['./update-password.component.css']
})
export class UpdatePasswordComponent {
  email: string = ''
  password: string = ''
  code: string = ''

  constructor(private updateClientPasswordService: UpdateClientPasswordService, private router: Router,
    private clientService: ClientService){
      let email = updateClientPasswordService.popEmail()
      if(email == null ){
        router.navigate([''])
      }else{
        this.email = email;
      }
  }

  updatePassword(){
    this.clientService.updatePassword(this.email, this.code, this.password).subscribe(res =>{
      this.router.navigate(['login'])
    })
  }
}
