import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IRequirementItem, RequirementItem } from '../requirement-item.model';
import { RequirementItemService } from '../service/requirement-item.service';

import { RequirementItemRoutingResolveService } from './requirement-item-routing-resolve.service';

describe('RequirementItem routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: RequirementItemRoutingResolveService;
  let service: RequirementItemService;
  let resultRequirementItem: IRequirementItem | undefined;

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
    routingResolveService = TestBed.inject(RequirementItemRoutingResolveService);
    service = TestBed.inject(RequirementItemService);
    resultRequirementItem = undefined;
  });

  describe('resolve', () => {
    it('should return IRequirementItem returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRequirementItem = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRequirementItem).toEqual({ id: 123 });
    });

    it('should return new IRequirementItem if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRequirementItem = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultRequirementItem).toEqual(new RequirementItem());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as RequirementItem })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRequirementItem = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRequirementItem).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
