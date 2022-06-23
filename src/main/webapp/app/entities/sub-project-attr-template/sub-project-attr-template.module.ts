import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SubProjectAttrTemplateComponent } from './list/sub-project-attr-template.component';
import { SubProjectAttrTemplateDetailComponent } from './detail/sub-project-attr-template-detail.component';
import { SubProjectAttrTemplateUpdateComponent } from './update/sub-project-attr-template-update.component';
import { SubProjectAttrTemplateDeleteDialogComponent } from './delete/sub-project-attr-template-delete-dialog.component';
import { SubProjectAttrTemplateRoutingModule } from './route/sub-project-attr-template-routing.module';

@NgModule({
  imports: [SharedModule, SubProjectAttrTemplateRoutingModule],
  declarations: [
    SubProjectAttrTemplateComponent,
    SubProjectAttrTemplateDetailComponent,
    SubProjectAttrTemplateUpdateComponent,
    SubProjectAttrTemplateDeleteDialogComponent,
  ],
  entryComponents: [SubProjectAttrTemplateDeleteDialogComponent],
})
export class SubProjectAttrTemplateModule {}
