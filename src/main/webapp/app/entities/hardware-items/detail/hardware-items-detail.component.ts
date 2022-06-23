import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHardwareItems } from '../hardware-items.model';

@Component({
  selector: 'jhi-hardware-items-detail',
  templateUrl: './hardware-items-detail.component.html',
})
export class HardwareItemsDetailComponent implements OnInit {
  hardwareItems: IHardwareItems | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hardwareItems }) => {
      this.hardwareItems = hardwareItems;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
