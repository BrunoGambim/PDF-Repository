import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ClientService } from 'src/app/services/client/client.service';
import { UpdateClientPasswordService } from 'src/app/services/client/update-client-password.service';

@Component({
  selector: 'app-recover-password',
  templateUrl: './recover-password.component.html',
  styleUrls: ['./recover-password.component.css']
})
export class RecoverPasswordComponent {
  email: string = ''

  constructor(private clientService: ClientService, private router: Router,
    private updateClientPasswordService: UpdateClientPasswordService){
  }

  sendPassordRecoverMessage(){
    this.clientService.sendPasswordUpdateCode(this.email).subscribe(res => {
      this.updateClientPasswordService.putEmail(this.email)
      this.router.navigate(['updatePassword'])
    })
  }
}
