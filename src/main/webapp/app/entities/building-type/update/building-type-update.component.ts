import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IBuildingType, BuildingType } from '../building-type.model';
import { BuildingTypeService } from '../service/building-type.service';

@Component({
  selector: 'jhi-building-type-update',
  templateUrl: './building-type-update.component.html',
})
export class BuildingTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    nameAr: [],
    nameEn: [],
  });

  constructor(protected buildingTypeService: BuildingTypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ buildingType }) => {
      this.updateForm(buildingType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const buildingType = this.createFromForm();
    if (buildingType.id !== undefined) {
      this.subscribeToSaveResponse(this.buildingTypeService.update(buildingType));
    } else {
      this.subscribeToSaveResponse(this.buildingTypeService.create(buildingType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBuildingType>>): void {
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

  protected updateForm(buildingType: IBuildingType): void {
    this.editForm.patchValue({
      id: buildingType.id,
      code: buildingType.code,
      nameAr: buildingType.nameAr,
      nameEn: buildingType.nameEn,
    });
  }

  protected createFromForm(): IBuildingType {
    return {
      ...new BuildingType(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      nameAr: this.editForm.get(['nameAr'])!.value,
      nameEn: this.editForm.get(['nameEn'])!.value,
    };
  }
}
