import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProjectItemsRequirement, getProjectItemsRequirementIdentifier } from '../project-items-requirement.model';

export type EntityResponseType = HttpResponse<IProjectItemsRequirement>;
export type EntityArrayResponseType = HttpResponse<IProjectItemsRequirement[]>;

@Injectable({ providedIn: 'root' })
export class ProjectItemsRequirementService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/project-items-requirements');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(projectItemsRequirement: IProjectItemsRequirement): Observable<EntityResponseType> {
    return this.http.post<IProjectItemsRequirement>(this.resourceUrl, projectItemsRequirement, { observe: 'response' });
  }

  update(projectItemsRequirement: IProjectItemsRequirement): Observable<EntityResponseType> {
    return this.http.put<IProjectItemsRequirement>(
      `${this.resourceUrl}/${getProjectItemsRequirementIdentifier(projectItemsRequirement) as number}`,
      projectItemsRequirement,
      { observe: 'response' }
    );
  }

  partialUpdate(projectItemsRequirement: IProjectItemsRequirement): Observable<EntityResponseType> {
    return this.http.patch<IProjectItemsRequirement>(
      `${this.resourceUrl}/${getProjectItemsRequirementIdentifier(projectItemsRequirement) as number}`,
      projectItemsRequirement,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectItemsRequirement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectItemsRequirement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProjectItemsRequirementToCollectionIfMissing(
    projectItemsRequirementCollection: IProjectItemsRequirement[],
    ...projectItemsRequirementsToCheck: (IProjectItemsRequirement | null | undefined)[]
  ): IProjectItemsRequirement[] {
    const projectItemsRequirements: IProjectItemsRequirement[] = projectItemsRequirementsToCheck.filter(isPresent);
    if (projectItemsRequirements.length > 0) {
      const projectItemsRequirementCollectionIdentifiers = projectItemsRequirementCollection.map(
        projectItemsRequirementItem => getProjectItemsRequirementIdentifier(projectItemsRequirementItem)!
      );
      const projectItemsRequirementsToAdd = projectItemsRequirements.filter(projectItemsRequirementItem => {
        const projectItemsRequirementIdentifier = getProjectItemsRequirementIdentifier(projectItemsRequirementItem);
        if (
          projectItemsRequirementIdentifier == null ||
          projectItemsRequirementCollectionIdentifiers.includes(projectItemsRequirementIdentifier)
        ) {
          return false;
        }
        projectItemsRequirementCollectionIdentifiers.push(projectItemsRequirementIdentifier);
        return true;
      });
      return [...projectItemsRequirementsToAdd, ...projectItemsRequirementCollection];
    }
    return projectItemsRequirementCollection;
  }
}
