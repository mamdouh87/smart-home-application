import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISubProjectAttrTemplate, SubProjectAttrTemplate } from '../sub-project-attr-template.model';
import { SubProjectAttrTemplateService } from '../service/sub-project-attr-template.service';
import { ISubProjectTemplate } from 'app/entities/sub-project-template/sub-project-template.model';
import { SubProjectTemplateService } from 'app/entities/sub-project-template/service/sub-project-template.service';

@Component({
  selector: 'jhi-sub-project-attr-template-update',
  templateUrl: './sub-project-attr-template-update.component.html',
})
export class SubProjectAttrTemplateUpdateComponent implements OnInit {
  isSaving = false;

  subProjectTemplatesSharedCollection: ISubProjectTemplate[] = [];

  editForm = this.fb.group({
    id: [],
    attrCode: [],
    attrCodeNameAr: [],
    attrCodeNameEn: [],
    attrType: [],
    subProjectTemplate: [],
  });

  constructor(
    protected subProjectAttrTemplateService: SubProjectAttrTemplateService,
    protected subProjectTemplateService: SubProjectTemplateService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subProjectAttrTemplate }) => {
      this.updateForm(subProjectAttrTemplate);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subProjectAttrTemplate = this.createFromForm();
    if (subProjectAttrTemplate.id !== undefined) {
      this.subscribeToSaveResponse(this.subProjectAttrTemplateService.update(subProjectAttrTemplate));
    } else {
      this.subscribeToSaveResponse(this.subProjectAttrTemplateService.create(subProjectAttrTemplate));
    }
  }

  trackSubProjectTemplateById(_index: number, item: ISubProjectTemplate): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubProjectAttrTemplate>>): void {
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

  protected updateForm(subProjectAttrTemplate: ISubProjectAttrTemplate): void {
    this.editForm.patchValue({
      id: subProjectAttrTemplate.id,
      attrCode: subProjectAttrTemplate.attrCode,
      attrCodeNameAr: subProjectAttrTemplate.attrCodeNameAr,
      attrCodeNameEn: subProjectAttrTemplate.attrCodeNameEn,
      attrType: subProjectAttrTemplate.attrType,
      subProjectTemplate: subProjectAttrTemplate.subProjectTemplate,
    });

    this.subProjectTemplatesSharedCollection = this.subProjectTemplateService.addSubProjectTemplateToCollectionIfMissing(
      this.subProjectTemplatesSharedCollection,
      subProjectAttrTemplate.subProjectTemplate
    );
  }

  protected loadRelationshipsOptions(): void {
    this.subProjectTemplateService
      .query()
      .pipe(map((res: HttpResponse<ISubProjectTemplate[]>) => res.body ?? []))
      .pipe(
        map((subProjectTemplates: ISubProjectTemplate[]) =>
          this.subProjectTemplateService.addSubProjectTemplateToCollectionIfMissing(
            subProjectTemplates,
            this.editForm.get('subProjectTemplate')!.value
          )
        )
      )
      .subscribe((subProjectTemplates: ISubProjectTemplate[]) => (this.subProjectTemplatesSharedCollection = subProjectTemplates));
  }

  protected createFromForm(): ISubProjectAttrTemplate {
    return {
      ...new SubProjectAttrTemplate(),
      id: this.editForm.get(['id'])!.value,
      attrCode: this.editForm.get(['attrCode'])!.value,
      attrCodeNameAr: this.editForm.get(['attrCodeNameAr'])!.value,
      attrCodeNameEn: this.editForm.get(['attrCodeNameEn'])!.value,
      attrType: this.editForm.get(['attrType'])!.value,
      subProjectTemplate: this.editForm.get(['subProjectTemplate'])!.value,
    };
  }
}
