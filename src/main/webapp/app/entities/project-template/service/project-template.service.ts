import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProjectTemplate, getProjectTemplateIdentifier } from '../project-template.model';

export type EntityResponseType = HttpResponse<IProjectTemplate>;
export type EntityArrayResponseType = HttpResponse<IProjectTemplate[]>;

@Injectable({ providedIn: 'root' })
export class ProjectTemplateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/project-templates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(projectTemplate: IProjectTemplate): Observable<EntityResponseType> {
    return this.http.post<IProjectTemplate>(this.resourceUrl, projectTemplate, { observe: 'response' });
  }

  update(projectTemplate: IProjectTemplate): Observable<EntityResponseType> {
    return this.http.put<IProjectTemplate>(
      `${this.resourceUrl}/${getProjectTemplateIdentifier(projectTemplate) as number}`,
      projectTemplate,
      { observe: 'response' }
    );
  }

  partialUpdate(projectTemplate: IProjectTemplate): Observable<EntityResponseType> {
    return this.http.patch<IProjectTemplate>(
      `${this.resourceUrl}/${getProjectTemplateIdentifier(projectTemplate) as number}`,
      projectTemplate,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectTemplate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProjectTemplateToCollectionIfMissing(
    projectTemplateCollection: IProjectTemplate[],
    ...projectTemplatesToCheck: (IProjectTemplate | null | undefined)[]
  ): IProjectTemplate[] {
    const projectTemplates: IProjectTemplate[] = projectTemplatesToCheck.filter(isPresent);
    if (projectTemplates.length > 0) {
      const projectTemplateCollectionIdentifiers = projectTemplateCollection.map(
        projectTemplateItem => getProjectTemplateIdentifier(projectTemplateItem)!
      );
      const projectTemplatesToAdd = projectTemplates.filter(projectTemplateItem => {
        const projectTemplateIdentifier = getProjectTemplateIdentifier(projectTemplateItem);
        if (projectTemplateIdentifier == null || projectTemplateCollectionIdentifiers.includes(projectTemplateIdentifier)) {
          return false;
        }
        projectTemplateCollectionIdentifiers.push(projectTemplateIdentifier);
        return true;
      });
      return [...projectTemplatesToAdd, ...projectTemplateCollection];
    }
    return projectTemplateCollection;
  }
}
