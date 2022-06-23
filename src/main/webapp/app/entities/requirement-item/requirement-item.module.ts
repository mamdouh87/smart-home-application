import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RequirementItemComponent } from './list/requirement-item.component';
import { RequirementItemDetailComponent } from './detail/requirement-item-detail.component';
import { RequirementItemUpdateComponent } from './update/requirement-item-update.component';
import { RequirementItemDeleteDialogComponent } from './delete/requirement-item-delete-dialog.component';
import { RequirementItemRoutingModule } from './route/requirement-item-routing.module';

@NgModule({
  imports: [SharedModule, RequirementItemRoutingModule],
  declarations: [
    RequirementItemComponent,
    RequirementItemDetailComponent,
    RequirementItemUpdateComponent,
    RequirementItemDeleteDialogComponent,
  ],
  entryComponents: [RequirementItemDeleteDialogComponent],
})
export class RequirementItemModule {}
