import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequirementItem, getRequirementItemIdentifier } from '../requirement-item.model';

export type EntityResponseType = HttpResponse<IRequirementItem>;
export type EntityArrayResponseType = HttpResponse<IRequirementItem[]>;

@Injectable({ providedIn: 'root' })
export class RequirementItemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/requirement-items');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(requirementItem: IRequirementItem): Observable<EntityResponseType> {
    return this.http.post<IRequirementItem>(this.resourceUrl, requirementItem, { observe: 'response' });
  }

  update(requirementItem: IRequirementItem): Observable<EntityResponseType> {
    return this.http.put<IRequirementItem>(
      `${this.resourceUrl}/${getRequirementItemIdentifier(requirementItem) as number}`,
      requirementItem,
      { observe: 'response' }
    );
  }

  partialUpdate(requirementItem: IRequirementItem): Observable<EntityResponseType> {
    return this.http.patch<IRequirementItem>(
      `${this.resourceUrl}/${getRequirementItemIdentifier(requirementItem) as number}`,
      requirementItem,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRequirementItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRequirementItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRequirementItemToCollectionIfMissing(
    requirementItemCollection: IRequirementItem[],
    ...requirementItemsToCheck: (IRequirementItem | null | undefined)[]
  ): IRequirementItem[] {
    const requirementItems: IRequirementItem[] = requirementItemsToCheck.filter(isPresent);
    if (requirementItems.length > 0) {
      const requirementItemCollectionIdentifiers = requirementItemCollection.map(
        requirementItemItem => getRequirementItemIdentifier(requirementItemItem)!
      );
      const requirementItemsToAdd = requirementItems.filter(requirementItemItem => {
        const requirementItemIdentifier = getRequirementItemIdentifier(requirementItemItem);
        if (requirementItemIdentifier == null || requirementItemCollectionIdentifiers.includes(requirementItemIdentifier)) {
          return false;
        }
        requirementItemCollectionIdentifiers.push(requirementItemIdentifier);
        return true;
      });
      return [...requirementItemsToAdd, ...requirementItemCollection];
    }
    return requirementItemCollection;
  }
}
