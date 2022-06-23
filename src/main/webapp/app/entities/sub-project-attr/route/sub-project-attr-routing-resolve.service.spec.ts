import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISubProjectAttr, SubProjectAttr } from '../sub-project-attr.model';
import { SubProjectAttrService } from '../service/sub-project-attr.service';

import { SubProjectAttrRoutingResolveService } from './sub-project-attr-routing-resolve.service';

describe('SubProjectAttr routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SubProjectAttrRoutingResolveService;
  let service: SubProjectAttrService;
  let resultSubProjectAttr: ISubProjectAttr | undefined;

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
    routingResolveService = TestBed.inject(SubProjectAttrRoutingResolveService);
    service = TestBed.inject(SubProjectAttrService);
    resultSubProjectAttr = undefined;
  });

  describe('resolve', () => {
    it('should return ISubProjectAttr returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubProjectAttr = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSubProjectAttr).toEqual({ id: 123 });
    });

    it('should return new ISubProjectAttr if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubProjectAttr = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSubProjectAttr).toEqual(new SubProjectAttr());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SubProjectAttr })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubProjectAttr = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSubProjectAttr).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
