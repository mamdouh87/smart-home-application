import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISubProjectAttrTemplate, getSubProjectAttrTemplateIdentifier } from '../sub-project-attr-template.model';

export type EntityResponseType = HttpResponse<ISubProjectAttrTemplate>;
export type EntityArrayResponseType = HttpResponse<ISubProjectAttrTemplate[]>;

@Injectable({ providedIn: 'root' })
export class SubProjectAttrTemplateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sub-project-attr-templates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(subProjectAttrTemplate: ISubProjectAttrTemplate): Observable<EntityResponseType> {
    return this.http.post<ISubProjectAttrTemplate>(this.resourceUrl, subProjectAttrTemplate, { observe: 'response' });
  }

  update(subProjectAttrTemplate: ISubProjectAttrTemplate): Observable<EntityResponseType> {
    return this.http.put<ISubProjectAttrTemplate>(
      `${this.resourceUrl}/${getSubProjectAttrTemplateIdentifier(subProjectAttrTemplate) as number}`,
      subProjectAttrTemplate,
      { observe: 'response' }
    );
  }

  partialUpdate(subProjectAttrTemplate: ISubProjectAttrTemplate): Observable<EntityResponseType> {
    return this.http.patch<ISubProjectAttrTemplate>(
      `${this.resourceUrl}/${getSubProjectAttrTemplateIdentifier(subProjectAttrTemplate) as number}`,
      subProjectAttrTemplate,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubProjectAttrTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubProjectAttrTemplate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSubProjectAttrTemplateToCollectionIfMissing(
    subProjectAttrTemplateCollection: ISubProjectAttrTemplate[],
    ...subProjectAttrTemplatesToCheck: (ISubProjectAttrTemplate | null | undefined)[]
  ): ISubProjectAttrTemplate[] {
    const subProjectAttrTemplates: ISubProjectAttrTemplate[] = subProjectAttrTemplatesToCheck.filter(isPresent);
    if (subProjectAttrTemplates.length > 0) {
      const subProjectAttrTemplateCollectionIdentifiers = subProjectAttrTemplateCollection.map(
        subProjectAttrTemplateItem => getSubProjectAttrTemplateIdentifier(subProjectAttrTemplateItem)!
      );
      const subProjectAttrTemplatesToAdd = subProjectAttrTemplates.filter(subProjectAttrTemplateItem => {
        const subProjectAttrTemplateIdentifier = getSubProjectAttrTemplateIdentifier(subProjectAttrTemplateItem);
        if (
          subProjectAttrTemplateIdentifier == null ||
          subProjectAttrTemplateCollectionIdentifiers.includes(subProjectAttrTemplateIdentifier)
        ) {
          return false;
        }
        subProjectAttrTemplateCollectionIdentifiers.push(subProjectAttrTemplateIdentifier);
        return true;
      });
      return [...subProjectAttrTemplatesToAdd, ...subProjectAttrTemplateCollection];
    }
    return subProjectAttrTemplateCollection;
  }
}
