import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProjectItemsRequirementComponent } from './list/project-items-requirement.component';
import { ProjectItemsRequirementDetailComponent } from './detail/project-items-requirement-detail.component';
import { ProjectItemsRequirementUpdateComponent } from './update/project-items-requirement-update.component';
import { ProjectItemsRequirementDeleteDialogComponent } from './delete/project-items-requirement-delete-dialog.component';
import { ProjectItemsRequirementRoutingModule } from './route/project-items-requirement-routing.module';

@NgModule({
  imports: [SharedModule, ProjectItemsRequirementRoutingModule],
  declarations: [
    ProjectItemsRequirementComponent,
    ProjectItemsRequirementDetailComponent,
    ProjectItemsRequirementUpdateComponent,
    ProjectItemsRequirementDeleteDialogComponent,
  ],
  entryComponents: [ProjectItemsRequirementDeleteDialogComponent],
})
export class ProjectItemsRequirementModule {}
