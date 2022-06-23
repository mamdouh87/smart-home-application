import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubProject } from '../sub-project.model';

@Component({
  selector: 'jhi-sub-project-detail',
  templateUrl: './sub-project-detail.component.html',
})
export class SubProjectDetailComponent implements OnInit {
  subProject: ISubProject | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subProject }) => {
      this.subProject = subProject;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
