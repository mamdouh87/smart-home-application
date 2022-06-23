import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISubProject, SubProject } from '../sub-project.model';
import { SubProjectService } from '../service/sub-project.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';

@Component({
  selector: 'jhi-sub-project-update',
  templateUrl: './sub-project-update.component.html',
})
export class SubProjectUpdateComponent implements OnInit {
  isSaving = false;

  projectsSharedCollection: IProject[] = [];

  editForm = this.fb.group({
    id: [],
    projectTemplateCode: [],
    projectTemplateNameAr: [],
    projectTemplateNameEn: [],
    project: [],
  });

  constructor(
    protected subProjectService: SubProjectService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subProject }) => {
      this.updateForm(subProject);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subProject = this.createFromForm();
    if (subProject.id !== undefined) {
      this.subscribeToSaveResponse(this.subProjectService.update(subProject));
    } else {
      this.subscribeToSaveResponse(this.subProjectService.create(subProject));
    }
  }

  trackProjectById(_index: number, item: IProject): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubProject>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(subProject: ISubProject): void {
    this.editForm.patchValue({
      id: subProject.id,
      projectTemplateCode: subProject.projectTemplateCode,
      projectTemplateNameAr: subProject.projectTemplateNameAr,
      projectTemplateNameEn: subProject.projectTemplateNameEn,
      project: subProject.project,
    });

    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing(this.projectsSharedCollection, subProject.project);
  }

  protected loadRelationshipsOptions(): void {
    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(
        map((projects: IProject[]) => this.projectService.addProjectToCollectionIfMissing(projects, this.editForm.get('project')!.value))
      )
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));
  }

  protected createFromForm(): ISubProject {
    return {
      ...new SubProject(),
      id: this.editForm.get(['id'])!.value,
      projectTemplateCode: this.editForm.get(['projectTemplateCode'])!.value,
      projectTemplateNameAr: this.editForm.get(['projectTemplateNameAr'])!.value,
      projectTemplateNameEn: this.editForm.get(['projectTemplateNameEn'])!.value,
      project: this.editForm.get(['project'])!.value,
    };
  }
}
