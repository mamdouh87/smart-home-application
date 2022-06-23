import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBuildingType, getBuildingTypeIdentifier } from '../building-type.model';

export type EntityResponseType = HttpResponse<IBuildingType>;
export type EntityArrayResponseType = HttpResponse<IBuildingType[]>;

@Injectable({ providedIn: 'root' })
export class BuildingTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/building-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(buildingType: IBuildingType): Observable<EntityResponseType> {
    return this.http.post<IBuildingType>(this.resourceUrl, buildingType, { observe: 'response' });
  }

  update(buildingType: IBuildingType): Observable<EntityResponseType> {
    return this.http.put<IBuildingType>(`${this.resourceUrl}/${getBuildingTypeIdentifier(buildingType) as number}`, buildingType, {
      observe: 'response',
    });
  }

  partialUpdate(buildingType: IBuildingType): Observable<EntityResponseType> {
    return this.http.patch<IBuildingType>(`${this.resourceUrl}/${getBuildingTypeIdentifier(buildingType) as number}`, buildingType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBuildingType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBuildingType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBuildingTypeToCollectionIfMissing(
    buildingTypeCollection: IBuildingType[],
    ...buildingTypesToCheck: (IBuildingType | null | undefined)[]
  ): IBuildingType[] {
    const buildingTypes: IBuildingType[] = buildingTypesToCheck.filter(isPresent);
    if (buildingTypes.length > 0) {
      const buildingTypeCollectionIdentifiers = buildingTypeCollection.map(
        buildingTypeItem => getBuildingTypeIdentifier(buildingTypeItem)!
      );
      const buildingTypesToAdd = buildingTypes.filter(buildingTypeItem => {
        const buildingTypeIdentifier = getBuildingTypeIdentifier(buildingTypeItem);
        if (buildingTypeIdentifier == null || buildingTypeCollectionIdentifiers.includes(buildingTypeIdentifier)) {
          return false;
        }
        buildingTypeCollectionIdentifiers.push(buildingTypeIdentifier);
        return true;
      });
      return [...buildingTypesToAdd, ...buildingTypeCollection];
    }
    return buildingTypeCollection;
  }
}
