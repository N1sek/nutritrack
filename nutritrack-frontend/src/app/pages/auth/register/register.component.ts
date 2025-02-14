import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [
    FormsModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  step = 1;
  userData = {
    email: '',
    name: '',
    age: '',
    weight: '',
    height: '',
    goal: '',
  }

  nextStep() {
    if (this.step < 3) {
      this.step++;
    }
  }

  constructor(private router: Router) {}

}
