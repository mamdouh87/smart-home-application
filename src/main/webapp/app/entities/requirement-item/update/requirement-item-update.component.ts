import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRequirementItem, RequirementItem } from '../requirement-item.model';
import { RequirementItemService } from '../service/requirement-item.service';
import { ISubProjectTemplate } from 'app/entities/sub-project-template/sub-project-template.model';
import { SubProjectTemplateService } from 'app/entities/sub-project-template/service/sub-project-template.service';

@Component({
  selector: 'jhi-requirement-item-update',
  templateUrl: './requirement-item-update.component.html',
})
export class RequirementItemUpdateComponent implements OnInit {
  isSaving = false;

  subProjectTemplatesSharedCollection: ISubProjectTemplate[] = [];

  editForm = this.fb.group({
    id: [],
    sysCode: [],
    code: [],
    descriptionAr: [],
    descriptionEn: [],
    subProjectTemplate: [],
  });

  constructor(
    protected requirementItemService: RequirementItemService,
    protected subProjectTemplateService: SubProjectTemplateService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requirementItem }) => {
      this.updateForm(requirementItem);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const requirementItem = this.createFromForm();
    if (requirementItem.id !== undefined) {
      this.subscribeToSaveResponse(this.requirementItemService.update(requirementItem));
    } else {
      this.subscribeToSaveResponse(this.requirementItemService.create(requirementItem));
    }
  }

  trackSubProjectTemplateById(_index: number, item: ISubProjectTemplate): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequirementItem>>): void {
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

  protected updateForm(requirementItem: IRequirementItem): void {
    this.editForm.patchValue({
      id: requirementItem.id,
      sysCode: requirementItem.sysCode,
      code: requirementItem.code,
      descriptionAr: requirementItem.descriptionAr,
      descriptionEn: requirementItem.descriptionEn,
      subProjectTemplate: requirementItem.subProjectTemplate,
    });

    this.subProjectTemplatesSharedCollection = this.subProjectTemplateService.addSubProjectTemplateToCollectionIfMissing(
      this.subProjectTemplatesSharedCollection,
      requirementItem.subProjectTemplate
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

  protected createFromForm(): IRequirementItem {
    return {
      ...new RequirementItem(),
      id: this.editForm.get(['id'])!.value,
      sysCode: this.editForm.get(['sysCode'])!.value,
      code: this.editForm.get(['code'])!.value,
      descriptionAr: this.editForm.get(['descriptionAr'])!.value,
      descriptionEn: this.editForm.get(['descriptionEn'])!.value,
      subProjectTemplate: this.editForm.get(['subProjectTemplate'])!.value,
    };
  }
}
