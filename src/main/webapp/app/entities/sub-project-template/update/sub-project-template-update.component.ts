import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISubProjectTemplate, SubProjectTemplate } from '../sub-project-template.model';
import { SubProjectTemplateService } from '../service/sub-project-template.service';
import { IProjectTemplate } from 'app/entities/project-template/project-template.model';
import { ProjectTemplateService } from 'app/entities/project-template/service/project-template.service';

@Component({
  selector: 'jhi-sub-project-template-update',
  templateUrl: './sub-project-template-update.component.html',
})
export class SubProjectTemplateUpdateComponent implements OnInit {
  isSaving = false;

  projectTemplatesSharedCollection: IProjectTemplate[] = [];

  editForm = this.fb.group({
    id: [],
    projectTemplateCode: [],
    projectTemplateNameAr: [],
    projectTemplateNameEn: [],
    projectTemplate: [],
  });

  constructor(
    protected subProjectTemplateService: SubProjectTemplateService,
    protected projectTemplateService: ProjectTemplateService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subProjectTemplate }) => {
      this.updateForm(subProjectTemplate);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subProjectTemplate = this.createFromForm();
    if (subProjectTemplate.id !== undefined) {
      this.subscribeToSaveResponse(this.subProjectTemplateService.update(subProjectTemplate));
    } else {
      this.subscribeToSaveResponse(this.subProjectTemplateService.create(subProjectTemplate));
    }
  }

  trackProjectTemplateById(_index: number, item: IProjectTemplate): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubProjectTemplate>>): void {
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

  protected updateForm(subProjectTemplate: ISubProjectTemplate): void {
    this.editForm.patchValue({
      id: subProjectTemplate.id,
      projectTemplateCode: subProjectTemplate.projectTemplateCode,
      projectTemplateNameAr: subProjectTemplate.projectTemplateNameAr,
      projectTemplateNameEn: subProjectTemplate.projectTemplateNameEn,
      projectTemplate: subProjectTemplate.projectTemplate,
    });

    this.projectTemplatesSharedCollection = this.projectTemplateService.addProjectTemplateToCollectionIfMissing(
      this.projectTemplatesSharedCollection,
      subProjectTemplate.projectTemplate
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectTemplateService
      .query()
      .pipe(map((res: HttpResponse<IProjectTemplate[]>) => res.body ?? []))
      .pipe(
        map((projectTemplates: IProjectTemplate[]) =>
          this.projectTemplateService.addProjectTemplateToCollectionIfMissing(projectTemplates, this.editForm.get('projectTemplate')!.value)
        )
      )
      .subscribe((projectTemplates: IProjectTemplate[]) => (this.projectTemplatesSharedCollection = projectTemplates));
  }

  protected createFromForm(): ISubProjectTemplate {
    return {
      ...new SubProjectTemplate(),
      id: this.editForm.get(['id'])!.value,
      projectTemplateCode: this.editForm.get(['projectTemplateCode'])!.value,
      projectTemplateNameAr: this.editForm.get(['projectTemplateNameAr'])!.value,
      projectTemplateNameEn: this.editForm.get(['projectTemplateNameEn'])!.value,
      projectTemplate: this.editForm.get(['projectTemplate'])!.value,
    };
  }
}
