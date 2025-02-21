import { Component } from '@angular/core';
import {NavbarComponent} from '../../../shared/components/navbar/navbar.component';
import {BoxComponent} from '../../../shared/components/box/box.component';

@Component({
  selector: 'app-diario-nutricion',
  imports: [
    NavbarComponent,
    BoxComponent
  ],
  templateUrl: './diario-nutricion.component.html',
  styleUrl: './diario-nutricion.component.scss'
})
export class DiarioNutricionComponent {

}
