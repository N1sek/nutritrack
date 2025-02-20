import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../../core/auth.service';

@Component({
  selector: 'app-login',
  imports: [],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit{

  constructor(private authService: AuthService) {
  }

  ngOnInit() {
    return this.authService.login();
  }

}
