import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISubProjectAttr, getSubProjectAttrIdentifier } from '../sub-project-attr.model';

export type EntityResponseType = HttpResponse<ISubProjectAttr>;
export type EntityArrayResponseType = HttpResponse<ISubProjectAttr[]>;

@Injectable({ providedIn: 'root' })
export class SubProjectAttrService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sub-project-attrs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(subProjectAttr: ISubProjectAttr): Observable<EntityResponseType> {
    return this.http.post<ISubProjectAttr>(this.resourceUrl, subProjectAttr, { observe: 'response' });
  }

  update(subProjectAttr: ISubProjectAttr): Observable<EntityResponseType> {
    return this.http.put<ISubProjectAttr>(`${this.resourceUrl}/${getSubProjectAttrIdentifier(subProjectAttr) as number}`, subProjectAttr, {
      observe: 'response',
    });
  }

  partialUpdate(subProjectAttr: ISubProjectAttr): Observable<EntityResponseType> {
    return this.http.patch<ISubProjectAttr>(
      `${this.resourceUrl}/${getSubProjectAttrIdentifier(subProjectAttr) as number}`,
      subProjectAttr,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubProjectAttr>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubProjectAttr[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSubProjectAttrToCollectionIfMissing(
    subProjectAttrCollection: ISubProjectAttr[],
    ...subProjectAttrsToCheck: (ISubProjectAttr | null | undefined)[]
  ): ISubProjectAttr[] {
    const subProjectAttrs: ISubProjectAttr[] = subProjectAttrsToCheck.filter(isPresent);
    if (subProjectAttrs.length > 0) {
      const subProjectAttrCollectionIdentifiers = subProjectAttrCollection.map(
        subProjectAttrItem => getSubProjectAttrIdentifier(subProjectAttrItem)!
      );
      const subProjectAttrsToAdd = subProjectAttrs.filter(subProjectAttrItem => {
        const subProjectAttrIdentifier = getSubProjectAttrIdentifier(subProjectAttrItem);
        if (subProjectAttrIdentifier == null || subProjectAttrCollectionIdentifiers.includes(subProjectAttrIdentifier)) {
          return false;
        }
        subProjectAttrCollectionIdentifiers.push(subProjectAttrIdentifier);
        return true;
      });
      return [...subProjectAttrsToAdd, ...subProjectAttrCollection];
    }
    return subProjectAttrCollection;
  }
}
