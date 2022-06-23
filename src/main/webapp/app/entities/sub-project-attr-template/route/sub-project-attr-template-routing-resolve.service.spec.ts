import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISubProjectAttrTemplate, SubProjectAttrTemplate } from '../sub-project-attr-template.model';
import { SubProjectAttrTemplateService } from '../service/sub-project-attr-template.service';

import { SubProjectAttrTemplateRoutingResolveService } from './sub-project-attr-template-routing-resolve.service';

describe('SubProjectAttrTemplate routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SubProjectAttrTemplateRoutingResolveService;
  let service: SubProjectAttrTemplateService;
  let resultSubProjectAttrTemplate: ISubProjectAttrTemplate | undefined;

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
    routingResolveService = TestBed.inject(SubProjectAttrTemplateRoutingResolveService);
    service = TestBed.inject(SubProjectAttrTemplateService);
    resultSubProjectAttrTemplate = undefined;
  });

  describe('resolve', () => {
    it('should return ISubProjectAttrTemplate returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubProjectAttrTemplate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSubProjectAttrTemplate).toEqual({ id: 123 });
    });

    it('should return new ISubProjectAttrTemplate if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubProjectAttrTemplate = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSubProjectAttrTemplate).toEqual(new SubProjectAttrTemplate());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SubProjectAttrTemplate })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubProjectAttrTemplate = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSubProjectAttrTemplate).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
