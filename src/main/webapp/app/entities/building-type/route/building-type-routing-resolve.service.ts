import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBuildingType, BuildingType } from '../building-type.model';
import { BuildingTypeService } from '../service/building-type.service';

@Injectable({ providedIn: 'root' })
export class BuildingTypeRoutingResolveService implements Resolve<IBuildingType> {
  constructor(protected service: BuildingTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBuildingType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((buildingType: HttpResponse<BuildingType>) => {
          if (buildingType.body) {
            return of(buildingType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BuildingType());
  }
}
