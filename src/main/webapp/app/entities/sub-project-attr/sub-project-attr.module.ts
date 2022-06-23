import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SubProjectAttrComponent } from './list/sub-project-attr.component';
import { SubProjectAttrDetailComponent } from './detail/sub-project-attr-detail.component';
import { SubProjectAttrUpdateComponent } from './update/sub-project-attr-update.component';
import { SubProjectAttrDeleteDialogComponent } from './delete/sub-project-attr-delete-dialog.component';
import { SubProjectAttrRoutingModule } from './route/sub-project-attr-routing.module';

@NgModule({
  imports: [SharedModule, SubProjectAttrRoutingModule],
  declarations: [
    SubProjectAttrComponent,
    SubProjectAttrDetailComponent,
    SubProjectAttrUpdateComponent,
    SubProjectAttrDeleteDialogComponent,
  ],
  entryComponents: [SubProjectAttrDeleteDialogComponent],
})
export class SubProjectAttrModule {}
