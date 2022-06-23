import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRequirementItem } from '../requirement-item.model';
import { RequirementItemService } from '../service/requirement-item.service';

@Component({
  templateUrl: './requirement-item-delete-dialog.component.html',
})
export class RequirementItemDeleteDialogComponent {
  requirementItem?: IRequirementItem;

  constructor(protected requirementItemService: RequirementItemService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.requirementItemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
