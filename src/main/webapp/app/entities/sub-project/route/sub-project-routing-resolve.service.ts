import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISubProject, SubProject } from '../sub-project.model';
import { SubProjectService } from '../service/sub-project.service';

@Injectable({ providedIn: 'root' })
export class SubProjectRoutingResolveService implements Resolve<ISubProject> {
  constructor(protected service: SubProjectService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubProject> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((subProject: HttpResponse<SubProject>) => {
          if (subProject.body) {
            return of(subProject.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SubProject());
  }
}
