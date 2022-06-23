import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBuildingType } from '../building-type.model';
import { BuildingTypeService } from '../service/building-type.service';

@Component({
  templateUrl: './building-type-delete-dialog.component.html',
})
export class BuildingTypeDeleteDialogComponent {
  buildingType?: IBuildingType;

  constructor(protected buildingTypeService: BuildingTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.buildingTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
