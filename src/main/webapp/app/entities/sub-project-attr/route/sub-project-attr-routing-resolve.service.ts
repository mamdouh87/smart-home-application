import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISubProjectAttr, SubProjectAttr } from '../sub-project-attr.model';
import { SubProjectAttrService } from '../service/sub-project-attr.service';

@Injectable({ providedIn: 'root' })
export class SubProjectAttrRoutingResolveService implements Resolve<ISubProjectAttr> {
  constructor(protected service: SubProjectAttrService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubProjectAttr> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((subProjectAttr: HttpResponse<SubProjectAttr>) => {
          if (subProjectAttr.body) {
            return of(subProjectAttr.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SubProjectAttr());
  }
}
