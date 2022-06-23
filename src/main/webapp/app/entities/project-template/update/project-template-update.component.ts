import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IProjectTemplate, ProjectTemplate } from '../project-template.model';
import { ProjectTemplateService } from '../service/project-template.service';

@Component({
  selector: 'jhi-project-template-update',
  templateUrl: './project-template-update.component.html',
})
export class ProjectTemplateUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    projectTemplateCode: [],
    projectTemplateNameAr: [],
    projectTemplateNameEn: [],
  });

  constructor(
    protected projectTemplateService: ProjectTemplateService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectTemplate }) => {
      this.updateForm(projectTemplate);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projectTemplate = this.createFromForm();
    if (projectTemplate.id !== undefined) {
      this.subscribeToSaveResponse(this.projectTemplateService.update(projectTemplate));
    } else {
      this.subscribeToSaveResponse(this.projectTemplateService.create(projectTemplate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectTemplate>>): void {
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

  protected updateForm(projectTemplate: IProjectTemplate): void {
    this.editForm.patchValue({
      id: projectTemplate.id,
      projectTemplateCode: projectTemplate.projectTemplateCode,
      projectTemplateNameAr: projectTemplate.projectTemplateNameAr,
      projectTemplateNameEn: projectTemplate.projectTemplateNameEn,
    });
  }

  protected createFromForm(): IProjectTemplate {
    return {
      ...new ProjectTemplate(),
      id: this.editForm.get(['id'])!.value,
      projectTemplateCode: this.editForm.get(['projectTemplateCode'])!.value,
      projectTemplateNameAr: this.editForm.get(['projectTemplateNameAr'])!.value,
      projectTemplateNameEn: this.editForm.get(['projectTemplateNameEn'])!.value,
    };
  }
}
