import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProjectTemplateComponent } from './list/project-template.component';
import { ProjectTemplateDetailComponent } from './detail/project-template-detail.component';
import { ProjectTemplateUpdateComponent } from './update/project-template-update.component';
import { ProjectTemplateDeleteDialogComponent } from './delete/project-template-delete-dialog.component';
import { ProjectTemplateRoutingModule } from './route/project-template-routing.module';

@NgModule({
  imports: [SharedModule, ProjectTemplateRoutingModule],
  declarations: [
    ProjectTemplateComponent,
    ProjectTemplateDetailComponent,
    ProjectTemplateUpdateComponent,
    ProjectTemplateDeleteDialogComponent,
  ],
  entryComponents: [ProjectTemplateDeleteDialogComponent],
})
export class ProjectTemplateModule {}
