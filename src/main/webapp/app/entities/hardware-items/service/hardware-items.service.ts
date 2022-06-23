import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHardwareItems, getHardwareItemsIdentifier } from '../hardware-items.model';

export type EntityResponseType = HttpResponse<IHardwareItems>;
export type EntityArrayResponseType = HttpResponse<IHardwareItems[]>;

@Injectable({ providedIn: 'root' })
export class HardwareItemsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/hardware-items');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(hardwareItems: IHardwareItems): Observable<EntityResponseType> {
    return this.http.post<IHardwareItems>(this.resourceUrl, hardwareItems, { observe: 'response' });
  }

  update(hardwareItems: IHardwareItems): Observable<EntityResponseType> {
    return this.http.put<IHardwareItems>(`${this.resourceUrl}/${getHardwareItemsIdentifier(hardwareItems) as number}`, hardwareItems, {
      observe: 'response',
    });
  }

  partialUpdate(hardwareItems: IHardwareItems): Observable<EntityResponseType> {
    return this.http.patch<IHardwareItems>(`${this.resourceUrl}/${getHardwareItemsIdentifier(hardwareItems) as number}`, hardwareItems, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHardwareItems>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHardwareItems[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHardwareItemsToCollectionIfMissing(
    hardwareItemsCollection: IHardwareItems[],
    ...hardwareItemsToCheck: (IHardwareItems | null | undefined)[]
  ): IHardwareItems[] {
    const hardwareItems: IHardwareItems[] = hardwareItemsToCheck.filter(isPresent);
    if (hardwareItems.length > 0) {
      const hardwareItemsCollectionIdentifiers = hardwareItemsCollection.map(
        hardwareItemsItem => getHardwareItemsIdentifier(hardwareItemsItem)!
      );
      const hardwareItemsToAdd = hardwareItems.filter(hardwareItemsItem => {
        const hardwareItemsIdentifier = getHardwareItemsIdentifier(hardwareItemsItem);
        if (hardwareItemsIdentifier == null || hardwareItemsCollectionIdentifiers.includes(hardwareItemsIdentifier)) {
          return false;
        }
        hardwareItemsCollectionIdentifiers.push(hardwareItemsIdentifier);
        return true;
      });
      return [...hardwareItemsToAdd, ...hardwareItemsCollection];
    }
    return hardwareItemsCollection;
  }
}
