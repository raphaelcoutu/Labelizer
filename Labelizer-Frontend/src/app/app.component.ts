import { Component } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { version } from '../../package.json';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  constructor(private titleService: Title) {
    let envName = environment.production ? 'PROD' : 'DEV';

    this.titleService.setTitle('Labelizer ' + version + ' ('+envName+')')
  }

}
