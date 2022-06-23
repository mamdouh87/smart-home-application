import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubProjectTemplate } from '../sub-project-template.model';

@Component({
  selector: 'jhi-sub-project-template-detail',
  templateUrl: './sub-project-template-detail.component.html',
})
export class SubProjectTemplateDetailComponent implements OnInit {
  subProjectTemplate: ISubProjectTemplate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subProjectTemplate }) => {
      this.subProjectTemplate = subProjectTemplate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
