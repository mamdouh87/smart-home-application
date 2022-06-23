import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IProjectItemsRequirement, ProjectItemsRequirement } from '../project-items-requirement.model';
import { ProjectItemsRequirementService } from '../service/project-items-requirement.service';

import { ProjectItemsRequirementRoutingResolveService } from './project-items-requirement-routing-resolve.service';

describe('ProjectItemsRequirement routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ProjectItemsRequirementRoutingResolveService;
  let service: ProjectItemsRequirementService;
  let resultProjectItemsRequirement: IProjectItemsRequirement | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(ProjectItemsRequirementRoutingResolveService);
    service = TestBed.inject(ProjectItemsRequirementService);
    resultProjectItemsRequirement = undefined;
  });

  describe('resolve', () => {
    it('should return IProjectItemsRequirement returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProjectItemsRequirement = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultProjectItemsRequirement).toEqual({ id: 123 });
    });

    it('should return new IProjectItemsRequirement if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProjectItemsRequirement = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultProjectItemsRequirement).toEqual(new ProjectItemsRequirement());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ProjectItemsRequirement })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProjectItemsRequirement = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultProjectItemsRequirement).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
