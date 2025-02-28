import { Component, AfterViewInit } from '@angular/core';
import { NavbarComponent } from '../../shared/components/navbar/navbar.component';
import { BoxComponent } from '../../shared/components/box/box.component';
import { PesoComponent } from '../../shared/components/peso/peso.component';
import Chart from 'chart.js/auto';

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
export class DashboardComponent implements AfterViewInit {

  caloriasConsumidas = 1800;
  objetivoCalorias = 2200;
  proteinasConsumidas = 120;
  objetivoProteinas = 150;
  carbohidratosConsumidos = 210;
  objetivoCarbohidratos = 250;
  grasasConsumidas = 80;
  objetivoGrasas = 90;

  ultimosAlimentos = [
    { nombre: 'Tostada con aguacate', calorias: 200, proteinas: 5, carbohidratos: 20, grasas: 10 },
    { nombre: 'Pollo a la plancha', calorias: 300, proteinas: 35, carbohidratos: 0, grasas: 8 },
    { nombre: 'Yogur con frutos secos', calorias: 250, proteinas: 10, carbohidratos: 30, grasas: 12 }
  ];

  ngAfterViewInit() {
    this.renderCaloriasChart();
    this.renderPesoChart();
  }

  renderCaloriasChart() {
    new Chart('caloriasChart', {
      type: 'line',
      data: {
        labels: ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo'],
        datasets: [{
          label: 'Calorías Consumidas',
          data: [1600, 1750, 1800, 1900, 1850, 2000, 1950],
          borderColor: '#4eabf9',
          tension: 0.3,
          fill: true,
          backgroundColor: 'rgba(78, 171, 249, 0.2)'
        }]
      }
    });
  }

  renderPesoChart() {
    new Chart('pesoChart', {
      type: 'bar',
      data: {
        labels: ['Semana 1', 'Semana 2', 'Semana 3', 'Semana 4'],
        datasets: [{
          label: 'Peso (kg)',
          data: [78, 77.5, 77, 76.8],
          backgroundColor: '#4eabf9'
        }]
      }
    });
  }

  get caloriasRestantes() {
    return this.objetivoCalorias - this.caloriasConsumidas;
  }

  get proteinasRestantes() {
    return this.objetivoProteinas - this.proteinasConsumidas;
  }

  get carbohidratosRestantes() {
    return this.objetivoCarbohidratos - this.carbohidratosConsumidos;
  }

  get grasasRestantes() {
    return this.objetivoGrasas - this.grasasConsumidas;
  }
}
