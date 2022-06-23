import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BuildingTypeComponent } from '../list/building-type.component';
import { BuildingTypeDetailComponent } from '../detail/building-type-detail.component';
import { BuildingTypeUpdateComponent } from '../update/building-type-update.component';
import { BuildingTypeRoutingResolveService } from './building-type-routing-resolve.service';

const buildingTypeRoute: Routes = [
  {
    path: '',
    component: BuildingTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BuildingTypeDetailComponent,
    resolve: {
      buildingType: BuildingTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BuildingTypeUpdateComponent,
    resolve: {
      buildingType: BuildingTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BuildingTypeUpdateComponent,
    resolve: {
      buildingType: BuildingTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(buildingTypeRoute)],
  exports: [RouterModule],
})
export class BuildingTypeRoutingModule {}
