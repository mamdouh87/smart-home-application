import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProjectItemsRequirementComponent } from '../list/project-items-requirement.component';
import { ProjectItemsRequirementDetailComponent } from '../detail/project-items-requirement-detail.component';
import { ProjectItemsRequirementUpdateComponent } from '../update/project-items-requirement-update.component';
import { ProjectItemsRequirementRoutingResolveService } from './project-items-requirement-routing-resolve.service';

const projectItemsRequirementRoute: Routes = [
  {
    path: '',
    component: ProjectItemsRequirementComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProjectItemsRequirementDetailComponent,
    resolve: {
      projectItemsRequirement: ProjectItemsRequirementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProjectItemsRequirementUpdateComponent,
    resolve: {
      projectItemsRequirement: ProjectItemsRequirementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProjectItemsRequirementUpdateComponent,
    resolve: {
      projectItemsRequirement: ProjectItemsRequirementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(projectItemsRequirementRoute)],
  exports: [RouterModule],
})
export class ProjectItemsRequirementRoutingModule {}
