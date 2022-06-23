import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubProjectTemplate } from '../sub-project-template.model';
import { SubProjectTemplateService } from '../service/sub-project-template.service';

@Component({
  templateUrl: './sub-project-template-delete-dialog.component.html',
})
export class SubProjectTemplateDeleteDialogComponent {
  subProjectTemplate?: ISubProjectTemplate;

  constructor(protected subProjectTemplateService: SubProjectTemplateService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subProjectTemplateService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
