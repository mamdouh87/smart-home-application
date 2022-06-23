import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubProjectAttr } from '../sub-project-attr.model';

@Component({
  selector: 'jhi-sub-project-attr-detail',
  templateUrl: './sub-project-attr-detail.component.html',
})
export class SubProjectAttrDetailComponent implements OnInit {
  subProjectAttr: ISubProjectAttr | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subProjectAttr }) => {
      this.subProjectAttr = subProjectAttr;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
