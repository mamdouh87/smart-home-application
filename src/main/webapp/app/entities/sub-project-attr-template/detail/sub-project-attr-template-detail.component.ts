import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubProjectAttrTemplate } from '../sub-project-attr-template.model';

@Component({
  selector: 'jhi-sub-project-attr-template-detail',
  templateUrl: './sub-project-attr-template-detail.component.html',
})
export class SubProjectAttrTemplateDetailComponent implements OnInit {
  subProjectAttrTemplate: ISubProjectAttrTemplate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subProjectAttrTemplate }) => {
      this.subProjectAttrTemplate = subProjectAttrTemplate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
