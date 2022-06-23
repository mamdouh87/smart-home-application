import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProjectTemplate, ProjectTemplate } from '../project-template.model';
import { ProjectTemplateService } from '../service/project-template.service';

@Injectable({ providedIn: 'root' })
export class ProjectTemplateRoutingResolveService implements Resolve<IProjectTemplate> {
  constructor(protected service: ProjectTemplateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjectTemplate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((projectTemplate: HttpResponse<ProjectTemplate>) => {
          if (projectTemplate.body) {
            return of(projectTemplate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProjectTemplate());
  }
}
