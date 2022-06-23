import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IHardwareItems, HardwareItems } from '../hardware-items.model';
import { HardwareItemsService } from '../service/hardware-items.service';
import { IRequirementItem } from 'app/entities/requirement-item/requirement-item.model';
import { RequirementItemService } from 'app/entities/requirement-item/service/requirement-item.service';

@Component({
  selector: 'jhi-hardware-items-update',
  templateUrl: './hardware-items-update.component.html',
})
export class HardwareItemsUpdateComponent implements OnInit {
  isSaving = false;

  itemsCollection: IRequirementItem[] = [];

  editForm = this.fb.group({
    id: [],
    hardwareDescAr: [],
    hardwareDescEn: [],
    supportedQty: [],
    item: [],
  });

  constructor(
    protected hardwareItemsService: HardwareItemsService,
    protected requirementItemService: RequirementItemService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hardwareItems }) => {
      this.updateForm(hardwareItems);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hardwareItems = this.createFromForm();
    if (hardwareItems.id !== undefined) {
      this.subscribeToSaveResponse(this.hardwareItemsService.update(hardwareItems));
    } else {
      this.subscribeToSaveResponse(this.hardwareItemsService.create(hardwareItems));
    }
  }

  trackRequirementItemById(_index: number, item: IRequirementItem): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHardwareItems>>): void {
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

  protected updateForm(hardwareItems: IHardwareItems): void {
    this.editForm.patchValue({
      id: hardwareItems.id,
      hardwareDescAr: hardwareItems.hardwareDescAr,
      hardwareDescEn: hardwareItems.hardwareDescEn,
      supportedQty: hardwareItems.supportedQty,
      item: hardwareItems.item,
    });

    this.itemsCollection = this.requirementItemService.addRequirementItemToCollectionIfMissing(this.itemsCollection, hardwareItems.item);
  }

  protected loadRelationshipsOptions(): void {
    this.requirementItemService
      .query({ filter: 'hardwareitems-is-null' })
      .pipe(map((res: HttpResponse<IRequirementItem[]>) => res.body ?? []))
      .pipe(
        map((requirementItems: IRequirementItem[]) =>
          this.requirementItemService.addRequirementItemToCollectionIfMissing(requirementItems, this.editForm.get('item')!.value)
        )
      )
      .subscribe((requirementItems: IRequirementItem[]) => (this.itemsCollection = requirementItems));
  }

  protected createFromForm(): IHardwareItems {
    return {
      ...new HardwareItems(),
      id: this.editForm.get(['id'])!.value,
      hardwareDescAr: this.editForm.get(['hardwareDescAr'])!.value,
      hardwareDescEn: this.editForm.get(['hardwareDescEn'])!.value,
      supportedQty: this.editForm.get(['supportedQty'])!.value,
      item: this.editForm.get(['item'])!.value,
    };
  }
}
