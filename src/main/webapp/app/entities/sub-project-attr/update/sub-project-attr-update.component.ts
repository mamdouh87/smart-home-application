import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISubProjectAttr, SubProjectAttr } from '../sub-project-attr.model';
import { SubProjectAttrService } from '../service/sub-project-attr.service';
import { ISubProject } from 'app/entities/sub-project/sub-project.model';
import { SubProjectService } from 'app/entities/sub-project/service/sub-project.service';

@Component({
  selector: 'jhi-sub-project-attr-update',
  templateUrl: './sub-project-attr-update.component.html',
})
export class SubProjectAttrUpdateComponent implements OnInit {
  isSaving = false;

  subProjectsSharedCollection: ISubProject[] = [];

  editForm = this.fb.group({
    id: [],
    attrCode: [],
    attrCodeNameAr: [],
    attrCodeNameEn: [],
    attrType: [],
    attrValue: [],
    subProject: [],
  });

  constructor(
    protected subProjectAttrService: SubProjectAttrService,
    protected subProjectService: SubProjectService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subProjectAttr }) => {
      this.updateForm(subProjectAttr);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subProjectAttr = this.createFromForm();
    if (subProjectAttr.id !== undefined) {
      this.subscribeToSaveResponse(this.subProjectAttrService.update(subProjectAttr));
    } else {
      this.subscribeToSaveResponse(this.subProjectAttrService.create(subProjectAttr));
    }
  }

  trackSubProjectById(_index: number, item: ISubProject): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubProjectAttr>>): void {
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

  protected updateForm(subProjectAttr: ISubProjectAttr): void {
    this.editForm.patchValue({
      id: subProjectAttr.id,
      attrCode: subProjectAttr.attrCode,
      attrCodeNameAr: subProjectAttr.attrCodeNameAr,
      attrCodeNameEn: subProjectAttr.attrCodeNameEn,
      attrType: subProjectAttr.attrType,
      attrValue: subProjectAttr.attrValue,
      subProject: subProjectAttr.subProject,
    });

    this.subProjectsSharedCollection = this.subProjectService.addSubProjectToCollectionIfMissing(
      this.subProjectsSharedCollection,
      subProjectAttr.subProject
    );
  }

  protected loadRelationshipsOptions(): void {
    this.subProjectService
      .query()
      .pipe(map((res: HttpResponse<ISubProject[]>) => res.body ?? []))
      .pipe(
        map((subProjects: ISubProject[]) =>
          this.subProjectService.addSubProjectToCollectionIfMissing(subProjects, this.editForm.get('subProject')!.value)
        )
      )
      .subscribe((subProjects: ISubProject[]) => (this.subProjectsSharedCollection = subProjects));
  }

  protected createFromForm(): ISubProjectAttr {
    return {
      ...new SubProjectAttr(),
      id: this.editForm.get(['id'])!.value,
      attrCode: this.editForm.get(['attrCode'])!.value,
      attrCodeNameAr: this.editForm.get(['attrCodeNameAr'])!.value,
      attrCodeNameEn: this.editForm.get(['attrCodeNameEn'])!.value,
      attrType: this.editForm.get(['attrType'])!.value,
      attrValue: this.editForm.get(['attrValue'])!.value,
      subProject: this.editForm.get(['subProject'])!.value,
    };
  }
}
