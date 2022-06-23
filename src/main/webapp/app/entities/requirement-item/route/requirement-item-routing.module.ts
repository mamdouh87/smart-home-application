import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RequirementItemComponent } from '../list/requirement-item.component';
import { RequirementItemDetailComponent } from '../detail/requirement-item-detail.component';
import { RequirementItemUpdateComponent } from '../update/requirement-item-update.component';
import { RequirementItemRoutingResolveService } from './requirement-item-routing-resolve.service';

const requirementItemRoute: Routes = [
  {
    path: '',
    component: RequirementItemComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RequirementItemDetailComponent,
    resolve: {
      requirementItem: RequirementItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RequirementItemUpdateComponent,
    resolve: {
      requirementItem: RequirementItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RequirementItemUpdateComponent,
    resolve: {
      requirementItem: RequirementItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(requirementItemRoute)],
  exports: [RouterModule],
})
export class RequirementItemRoutingModule {}
