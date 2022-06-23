import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubProjectAttrTemplate } from '../sub-project-attr-template.model';
import { SubProjectAttrTemplateService } from '../service/sub-project-attr-template.service';

@Component({
  templateUrl: './sub-project-attr-template-delete-dialog.component.html',
})
export class SubProjectAttrTemplateDeleteDialogComponent {
  subProjectAttrTemplate?: ISubProjectAttrTemplate;

  constructor(protected subProjectAttrTemplateService: SubProjectAttrTemplateService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subProjectAttrTemplateService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
