import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequirementItem } from '../requirement-item.model';

@Component({
  selector: 'jhi-requirement-item-detail',
  templateUrl: './requirement-item-detail.component.html',
})
export class RequirementItemDetailComponent implements OnInit {
  requirementItem: IRequirementItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requirementItem }) => {
      this.requirementItem = requirementItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
