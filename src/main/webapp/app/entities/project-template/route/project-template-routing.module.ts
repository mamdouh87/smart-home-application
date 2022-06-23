import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProjectTemplateComponent } from '../list/project-template.component';
import { ProjectTemplateDetailComponent } from '../detail/project-template-detail.component';
import { ProjectTemplateUpdateComponent } from '../update/project-template-update.component';
import { ProjectTemplateRoutingResolveService } from './project-template-routing-resolve.service';

const projectTemplateRoute: Routes = [
  {
    path: '',
    component: ProjectTemplateComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProjectTemplateDetailComponent,
    resolve: {
      projectTemplate: ProjectTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProjectTemplateUpdateComponent,
    resolve: {
      projectTemplate: ProjectTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProjectTemplateUpdateComponent,
    resolve: {
      projectTemplate: ProjectTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(projectTemplateRoute)],
  exports: [RouterModule],
})
export class ProjectTemplateRoutingModule {}
