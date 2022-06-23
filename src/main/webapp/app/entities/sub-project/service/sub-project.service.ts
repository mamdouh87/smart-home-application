import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISubProject, getSubProjectIdentifier } from '../sub-project.model';

export type EntityResponseType = HttpResponse<ISubProject>;
export type EntityArrayResponseType = HttpResponse<ISubProject[]>;

@Injectable({ providedIn: 'root' })
export class SubProjectService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sub-projects');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(subProject: ISubProject): Observable<EntityResponseType> {
    return this.http.post<ISubProject>(this.resourceUrl, subProject, { observe: 'response' });
  }

  update(subProject: ISubProject): Observable<EntityResponseType> {
    return this.http.put<ISubProject>(`${this.resourceUrl}/${getSubProjectIdentifier(subProject) as number}`, subProject, {
      observe: 'response',
    });
  }

  partialUpdate(subProject: ISubProject): Observable<EntityResponseType> {
    return this.http.patch<ISubProject>(`${this.resourceUrl}/${getSubProjectIdentifier(subProject) as number}`, subProject, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubProject>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubProject[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSubProjectToCollectionIfMissing(
    subProjectCollection: ISubProject[],
    ...subProjectsToCheck: (ISubProject | null | undefined)[]
  ): ISubProject[] {
    const subProjects: ISubProject[] = subProjectsToCheck.filter(isPresent);
    if (subProjects.length > 0) {
      const subProjectCollectionIdentifiers = subProjectCollection.map(subProjectItem => getSubProjectIdentifier(subProjectItem)!);
      const subProjectsToAdd = subProjects.filter(subProjectItem => {
        const subProjectIdentifier = getSubProjectIdentifier(subProjectItem);
        if (subProjectIdentifier == null || subProjectCollectionIdentifiers.includes(subProjectIdentifier)) {
          return false;
        }
        subProjectCollectionIdentifiers.push(subProjectIdentifier);
        return true;
      });
      return [...subProjectsToAdd, ...subProjectCollection];
    }
    return subProjectCollection;
  }
}
