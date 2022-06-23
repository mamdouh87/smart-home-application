import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProjectItemsRequirement } from '../project-items-requirement.model';
import { ProjectItemsRequirementService } from '../service/project-items-requirement.service';

@Component({
  templateUrl: './project-items-requirement-delete-dialog.component.html',
})
export class ProjectItemsRequirementDeleteDialogComponent {
  projectItemsRequirement?: IProjectItemsRequirement;

  constructor(protected projectItemsRequirementService: ProjectItemsRequirementService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.projectItemsRequirementService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
