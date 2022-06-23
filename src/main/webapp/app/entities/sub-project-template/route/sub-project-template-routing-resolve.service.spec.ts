import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISubProjectTemplate, SubProjectTemplate } from '../sub-project-template.model';
import { SubProjectTemplateService } from '../service/sub-project-template.service';

import { SubProjectTemplateRoutingResolveService } from './sub-project-template-routing-resolve.service';

describe('SubProjectTemplate routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SubProjectTemplateRoutingResolveService;
  let service: SubProjectTemplateService;
  let resultSubProjectTemplate: ISubProjectTemplate | undefined;

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
    routingResolveService = TestBed.inject(SubProjectTemplateRoutingResolveService);
    service = TestBed.inject(SubProjectTemplateService);
    resultSubProjectTemplate = undefined;
  });

  describe('resolve', () => {
    it('should return ISubProjectTemplate returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubProjectTemplate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSubProjectTemplate).toEqual({ id: 123 });
    });

    it('should return new ISubProjectTemplate if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubProjectTemplate = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSubProjectTemplate).toEqual(new SubProjectTemplate());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SubProjectTemplate })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubProjectTemplate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSubProjectTemplate).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
