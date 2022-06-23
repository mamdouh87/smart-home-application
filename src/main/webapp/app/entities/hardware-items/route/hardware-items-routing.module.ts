import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HardwareItemsComponent } from '../list/hardware-items.component';
import { HardwareItemsDetailComponent } from '../detail/hardware-items-detail.component';
import { HardwareItemsUpdateComponent } from '../update/hardware-items-update.component';
import { HardwareItemsRoutingResolveService } from './hardware-items-routing-resolve.service';

const hardwareItemsRoute: Routes = [
  {
    path: '',
    component: HardwareItemsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HardwareItemsDetailComponent,
    resolve: {
      hardwareItems: HardwareItemsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HardwareItemsUpdateComponent,
    resolve: {
      hardwareItems: HardwareItemsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HardwareItemsUpdateComponent,
    resolve: {
      hardwareItems: HardwareItemsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(hardwareItemsRoute)],
  exports: [RouterModule],
})
export class HardwareItemsRoutingModule {}
