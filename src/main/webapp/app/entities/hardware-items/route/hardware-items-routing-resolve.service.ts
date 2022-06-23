import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHardwareItems, HardwareItems } from '../hardware-items.model';
import { HardwareItemsService } from '../service/hardware-items.service';

@Injectable({ providedIn: 'root' })
export class HardwareItemsRoutingResolveService implements Resolve<IHardwareItems> {
  constructor(protected service: HardwareItemsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHardwareItems> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((hardwareItems: HttpResponse<HardwareItems>) => {
          if (hardwareItems.body) {
            return of(hardwareItems.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HardwareItems());
  }
}
