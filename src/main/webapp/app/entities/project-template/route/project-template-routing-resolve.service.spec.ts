import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IProjectTemplate, ProjectTemplate } from '../project-template.model';
import { ProjectTemplateService } from '../service/project-template.service';

import { ProjectTemplateRoutingResolveService } from './project-template-routing-resolve.service';

describe('ProjectTemplate routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ProjectTemplateRoutingResolveService;
  let service: ProjectTemplateService;
  let resultProjectTemplate: IProjectTemplate | undefined;

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
    routingResolveService = TestBed.inject(ProjectTemplateRoutingResolveService);
    service = TestBed.inject(ProjectTemplateService);
    resultProjectTemplate = undefined;
  });

  describe('resolve', () => {
    it('should return IProjectTemplate returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProjectTemplate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultProjectTemplate).toEqual({ id: 123 });
    });

    it('should return new IProjectTemplate if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProjectTemplate = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultProjectTemplate).toEqual(new ProjectTemplate());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ProjectTemplate })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProjectTemplate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultProjectTemplate).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
