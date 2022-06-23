import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SubProjectTemplateComponent } from './list/sub-project-template.component';
import { SubProjectTemplateDetailComponent } from './detail/sub-project-template-detail.component';
import { SubProjectTemplateUpdateComponent } from './update/sub-project-template-update.component';
import { SubProjectTemplateDeleteDialogComponent } from './delete/sub-project-template-delete-dialog.component';
import { SubProjectTemplateRoutingModule } from './route/sub-project-template-routing.module';

@NgModule({
  imports: [SharedModule, SubProjectTemplateRoutingModule],
  declarations: [
    SubProjectTemplateComponent,
    SubProjectTemplateDetailComponent,
    SubProjectTemplateUpdateComponent,
    SubProjectTemplateDeleteDialogComponent,
  ],
  entryComponents: [SubProjectTemplateDeleteDialogComponent],
})
export class SubProjectTemplateModule {}
