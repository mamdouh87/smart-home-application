import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProjectTemplate } from '../project-template.model';
import { ProjectTemplateService } from '../service/project-template.service';

@Component({
  templateUrl: './project-template-delete-dialog.component.html',
})
export class ProjectTemplateDeleteDialogComponent {
  projectTemplate?: IProjectTemplate;

  constructor(protected projectTemplateService: ProjectTemplateService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.projectTemplateService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
