import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubProject } from '../sub-project.model';
import { SubProjectService } from '../service/sub-project.service';

@Component({
  templateUrl: './sub-project-delete-dialog.component.html',
})
export class SubProjectDeleteDialogComponent {
  subProject?: ISubProject;

  constructor(protected subProjectService: SubProjectService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subProjectService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
