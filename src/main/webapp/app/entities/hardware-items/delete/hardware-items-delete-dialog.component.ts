import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHardwareItems } from '../hardware-items.model';
import { HardwareItemsService } from '../service/hardware-items.service';

@Component({
  templateUrl: './hardware-items-delete-dialog.component.html',
})
export class HardwareItemsDeleteDialogComponent {
  hardwareItems?: IHardwareItems;

  constructor(protected hardwareItemsService: HardwareItemsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hardwareItemsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
