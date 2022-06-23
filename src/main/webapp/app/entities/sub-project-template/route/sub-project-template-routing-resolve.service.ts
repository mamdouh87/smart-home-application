import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISubProjectTemplate, SubProjectTemplate } from '../sub-project-template.model';
import { SubProjectTemplateService } from '../service/sub-project-template.service';

@Injectable({ providedIn: 'root' })
export class SubProjectTemplateRoutingResolveService implements Resolve<ISubProjectTemplate> {
  constructor(protected service: SubProjectTemplateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubProjectTemplate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((subProjectTemplate: HttpResponse<SubProjectTemplate>) => {
          if (subProjectTemplate.body) {
            return of(subProjectTemplate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SubProjectTemplate());
  }
}
