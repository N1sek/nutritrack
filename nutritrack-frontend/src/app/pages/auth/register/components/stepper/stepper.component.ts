import {Component, Input} from '@angular/core';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'register-stepper',
  imports: [
    NgForOf
  ],
  templateUrl: './stepper.component.html',
  styleUrl: './stepper.component.scss'
})
export class StepperComponent {
  @Input() totalSteps: number = 4;
  @Input() currentStep: number = 1;

}
