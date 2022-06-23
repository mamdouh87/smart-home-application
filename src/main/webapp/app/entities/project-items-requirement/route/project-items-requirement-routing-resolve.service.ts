import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProjectItemsRequirement, ProjectItemsRequirement } from '../project-items-requirement.model';
import { ProjectItemsRequirementService } from '../service/project-items-requirement.service';

@Injectable({ providedIn: 'root' })
export class ProjectItemsRequirementRoutingResolveService implements Resolve<IProjectItemsRequirement> {
  constructor(protected service: ProjectItemsRequirementService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjectItemsRequirement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((projectItemsRequirement: HttpResponse<ProjectItemsRequirement>) => {
          if (projectItemsRequirement.body) {
            return of(projectItemsRequirement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProjectItemsRequirement());
  }
}
