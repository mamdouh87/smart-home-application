import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SubProjectAttrTemplateComponent } from '../list/sub-project-attr-template.component';
import { SubProjectAttrTemplateDetailComponent } from '../detail/sub-project-attr-template-detail.component';
import { SubProjectAttrTemplateUpdateComponent } from '../update/sub-project-attr-template-update.component';
import { SubProjectAttrTemplateRoutingResolveService } from './sub-project-attr-template-routing-resolve.service';

const subProjectAttrTemplateRoute: Routes = [
  {
    path: '',
    component: SubProjectAttrTemplateComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubProjectAttrTemplateDetailComponent,
    resolve: {
      subProjectAttrTemplate: SubProjectAttrTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubProjectAttrTemplateUpdateComponent,
    resolve: {
      subProjectAttrTemplate: SubProjectAttrTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubProjectAttrTemplateUpdateComponent,
    resolve: {
      subProjectAttrTemplate: SubProjectAttrTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(subProjectAttrTemplateRoute)],
  exports: [RouterModule],
})
export class SubProjectAttrTemplateRoutingModule {}
