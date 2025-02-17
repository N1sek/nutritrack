import { Component } from '@angular/core';
import {NgOptimizedImage} from '@angular/common';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.scss',
  standalone: true
})
export class LandingComponent {

  constructor(private router: Router) {}

  goToLogin() {
    this.router.navigate(['/login']);
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }
}
