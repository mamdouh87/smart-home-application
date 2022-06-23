import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISubProjectAttrTemplate, SubProjectAttrTemplate } from '../sub-project-attr-template.model';
import { SubProjectAttrTemplateService } from '../service/sub-project-attr-template.service';

@Injectable({ providedIn: 'root' })
export class SubProjectAttrTemplateRoutingResolveService implements Resolve<ISubProjectAttrTemplate> {
  constructor(protected service: SubProjectAttrTemplateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubProjectAttrTemplate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((subProjectAttrTemplate: HttpResponse<SubProjectAttrTemplate>) => {
          if (subProjectAttrTemplate.body) {
            return of(subProjectAttrTemplate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SubProjectAttrTemplate());
  }
}
