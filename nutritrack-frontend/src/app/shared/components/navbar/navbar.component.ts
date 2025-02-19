import { Component } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-navbar',
  imports: [],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {

  constructor(private router: Router) {}

  goToLogin() {
    // this.router.navigate(['/login']);
    this.router.navigate(['/dashboard']) // Temporal
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }
}
