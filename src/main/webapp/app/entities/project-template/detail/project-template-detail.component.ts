import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectTemplate } from '../project-template.model';

@Component({
  selector: 'jhi-project-template-detail',
  templateUrl: './project-template-detail.component.html',
})
export class ProjectTemplateDetailComponent implements OnInit {
  projectTemplate: IProjectTemplate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectTemplate }) => {
      this.projectTemplate = projectTemplate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
