import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SubProjectTemplateComponent } from '../list/sub-project-template.component';
import { SubProjectTemplateDetailComponent } from '../detail/sub-project-template-detail.component';
import { SubProjectTemplateUpdateComponent } from '../update/sub-project-template-update.component';
import { SubProjectTemplateRoutingResolveService } from './sub-project-template-routing-resolve.service';

const subProjectTemplateRoute: Routes = [
  {
    path: '',
    component: SubProjectTemplateComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubProjectTemplateDetailComponent,
    resolve: {
      subProjectTemplate: SubProjectTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubProjectTemplateUpdateComponent,
    resolve: {
      subProjectTemplate: SubProjectTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubProjectTemplateUpdateComponent,
    resolve: {
      subProjectTemplate: SubProjectTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(subProjectTemplateRoute)],
  exports: [RouterModule],
})
export class SubProjectTemplateRoutingModule {}
