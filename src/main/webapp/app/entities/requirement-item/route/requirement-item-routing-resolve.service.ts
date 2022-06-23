import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRequirementItem, RequirementItem } from '../requirement-item.model';
import { RequirementItemService } from '../service/requirement-item.service';

@Injectable({ providedIn: 'root' })
export class RequirementItemRoutingResolveService implements Resolve<IRequirementItem> {
  constructor(protected service: RequirementItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRequirementItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((requirementItem: HttpResponse<RequirementItem>) => {
          if (requirementItem.body) {
            return of(requirementItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RequirementItem());
  }
}
