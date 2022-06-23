import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SubProjectComponent } from './list/sub-project.component';
import { SubProjectDetailComponent } from './detail/sub-project-detail.component';
import { SubProjectUpdateComponent } from './update/sub-project-update.component';
import { SubProjectDeleteDialogComponent } from './delete/sub-project-delete-dialog.component';
import { SubProjectRoutingModule } from './route/sub-project-routing.module';

@NgModule({
  imports: [SharedModule, SubProjectRoutingModule],
  declarations: [SubProjectComponent, SubProjectDetailComponent, SubProjectUpdateComponent, SubProjectDeleteDialogComponent],
  entryComponents: [SubProjectDeleteDialogComponent],
})
export class SubProjectModule {}
