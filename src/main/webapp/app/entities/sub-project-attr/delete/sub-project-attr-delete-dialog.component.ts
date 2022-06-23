import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubProjectAttr } from '../sub-project-attr.model';
import { SubProjectAttrService } from '../service/sub-project-attr.service';

@Component({
  templateUrl: './sub-project-attr-delete-dialog.component.html',
})
export class SubProjectAttrDeleteDialogComponent {
  subProjectAttr?: ISubProjectAttr;

  constructor(protected subProjectAttrService: SubProjectAttrService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subProjectAttrService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
