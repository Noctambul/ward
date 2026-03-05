import { Component, signal } from '@angular/core';
import { LayoutComponent } from './features/layout/layout.component';

@Component({
  selector: 'app-root',
  imports: [LayoutComponent],
  template: `<app-layout />`,
})
export class App {
  protected readonly title = signal('web');
}
