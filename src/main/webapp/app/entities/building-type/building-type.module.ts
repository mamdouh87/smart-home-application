import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BuildingTypeComponent } from './list/building-type.component';
import { BuildingTypeDetailComponent } from './detail/building-type-detail.component';
import { BuildingTypeUpdateComponent } from './update/building-type-update.component';
import { BuildingTypeDeleteDialogComponent } from './delete/building-type-delete-dialog.component';
import { BuildingTypeRoutingModule } from './route/building-type-routing.module';

@NgModule({
  imports: [SharedModule, BuildingTypeRoutingModule],
  declarations: [BuildingTypeComponent, BuildingTypeDetailComponent, BuildingTypeUpdateComponent, BuildingTypeDeleteDialogComponent],
  entryComponents: [BuildingTypeDeleteDialogComponent],
})
export class BuildingTypeModule {}
