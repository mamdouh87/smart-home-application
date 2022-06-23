import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SubProjectComponent } from '../list/sub-project.component';
import { SubProjectDetailComponent } from '../detail/sub-project-detail.component';
import { SubProjectUpdateComponent } from '../update/sub-project-update.component';
import { SubProjectRoutingResolveService } from './sub-project-routing-resolve.service';

const subProjectRoute: Routes = [
  {
    path: '',
    component: SubProjectComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubProjectDetailComponent,
    resolve: {
      subProject: SubProjectRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubProjectUpdateComponent,
    resolve: {
      subProject: SubProjectRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubProjectUpdateComponent,
    resolve: {
      subProject: SubProjectRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(subProjectRoute)],
  exports: [RouterModule],
})
export class SubProjectRoutingModule {}
