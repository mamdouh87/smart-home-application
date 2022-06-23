import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HardwareItemsComponent } from './list/hardware-items.component';
import { HardwareItemsDetailComponent } from './detail/hardware-items-detail.component';
import { HardwareItemsUpdateComponent } from './update/hardware-items-update.component';
import { HardwareItemsDeleteDialogComponent } from './delete/hardware-items-delete-dialog.component';
import { HardwareItemsRoutingModule } from './route/hardware-items-routing.module';

@NgModule({
  imports: [SharedModule, HardwareItemsRoutingModule],
  declarations: [HardwareItemsComponent, HardwareItemsDetailComponent, HardwareItemsUpdateComponent, HardwareItemsDeleteDialogComponent],
  entryComponents: [HardwareItemsDeleteDialogComponent],
})
export class HardwareItemsModule {}
