import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SubProjectAttrComponent } from '../list/sub-project-attr.component';
import { SubProjectAttrDetailComponent } from '../detail/sub-project-attr-detail.component';
import { SubProjectAttrUpdateComponent } from '../update/sub-project-attr-update.component';
import { SubProjectAttrRoutingResolveService } from './sub-project-attr-routing-resolve.service';

const subProjectAttrRoute: Routes = [
  {
    path: '',
    component: SubProjectAttrComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubProjectAttrDetailComponent,
    resolve: {
      subProjectAttr: SubProjectAttrRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubProjectAttrUpdateComponent,
    resolve: {
      subProjectAttr: SubProjectAttrRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubProjectAttrUpdateComponent,
    resolve: {
      subProjectAttr: SubProjectAttrRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(subProjectAttrRoute)],
  exports: [RouterModule],
})
export class SubProjectAttrRoutingModule {}
