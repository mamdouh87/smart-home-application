import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBuildingType } from '../building-type.model';

@Component({
  selector: 'jhi-building-type-detail',
  templateUrl: './building-type-detail.component.html',
})
export class BuildingTypeDetailComponent implements OnInit {
  buildingType: IBuildingType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ buildingType }) => {
      this.buildingType = buildingType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
