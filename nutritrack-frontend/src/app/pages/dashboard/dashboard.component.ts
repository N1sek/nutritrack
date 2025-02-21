import { Component } from '@angular/core';
import {NavbarComponent} from '../../shared/components/navbar/navbar.component';
import {BoxComponent} from '../../shared/components/box/box.component';
import {PesoComponent} from '../../shared/components/peso/peso.component';

@Component({
  selector: 'app-dashboard',
  imports: [
    NavbarComponent,
    BoxComponent,
    PesoComponent
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

}
