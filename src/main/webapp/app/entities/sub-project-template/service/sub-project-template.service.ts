import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISubProjectTemplate, getSubProjectTemplateIdentifier } from '../sub-project-template.model';

export type EntityResponseType = HttpResponse<ISubProjectTemplate>;
export type EntityArrayResponseType = HttpResponse<ISubProjectTemplate[]>;

@Injectable({ providedIn: 'root' })
export class SubProjectTemplateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sub-project-templates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(subProjectTemplate: ISubProjectTemplate): Observable<EntityResponseType> {
    return this.http.post<ISubProjectTemplate>(this.resourceUrl, subProjectTemplate, { observe: 'response' });
  }

  update(subProjectTemplate: ISubProjectTemplate): Observable<EntityResponseType> {
    return this.http.put<ISubProjectTemplate>(
      `${this.resourceUrl}/${getSubProjectTemplateIdentifier(subProjectTemplate) as number}`,
      subProjectTemplate,
      { observe: 'response' }
    );
  }

  partialUpdate(subProjectTemplate: ISubProjectTemplate): Observable<EntityResponseType> {
    return this.http.patch<ISubProjectTemplate>(
      `${this.resourceUrl}/${getSubProjectTemplateIdentifier(subProjectTemplate) as number}`,
      subProjectTemplate,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubProjectTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubProjectTemplate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSubProjectTemplateToCollectionIfMissing(
    subProjectTemplateCollection: ISubProjectTemplate[],
    ...subProjectTemplatesToCheck: (ISubProjectTemplate | null | undefined)[]
  ): ISubProjectTemplate[] {
    const subProjectTemplates: ISubProjectTemplate[] = subProjectTemplatesToCheck.filter(isPresent);
    if (subProjectTemplates.length > 0) {
      const subProjectTemplateCollectionIdentifiers = subProjectTemplateCollection.map(
        subProjectTemplateItem => getSubProjectTemplateIdentifier(subProjectTemplateItem)!
      );
      const subProjectTemplatesToAdd = subProjectTemplates.filter(subProjectTemplateItem => {
        const subProjectTemplateIdentifier = getSubProjectTemplateIdentifier(subProjectTemplateItem);
        if (subProjectTemplateIdentifier == null || subProjectTemplateCollectionIdentifiers.includes(subProjectTemplateIdentifier)) {
          return false;
        }
        subProjectTemplateCollectionIdentifiers.push(subProjectTemplateIdentifier);
        return true;
      });
      return [...subProjectTemplatesToAdd, ...subProjectTemplateCollection];
    }
    return subProjectTemplateCollection;
  }
}
