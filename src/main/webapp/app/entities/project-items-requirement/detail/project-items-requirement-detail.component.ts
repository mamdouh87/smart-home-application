import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectItemsRequirement } from '../project-items-requirement.model';

@Component({
  selector: 'jhi-project-items-requirement-detail',
  templateUrl: './project-items-requirement-detail.component.html',
})
export class ProjectItemsRequirementDetailComponent implements OnInit {
  projectItemsRequirement: IProjectItemsRequirement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectItemsRequirement }) => {
      this.projectItemsRequirement = projectItemsRequirement;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
